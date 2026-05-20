package kz.ualikhan.booktrackers.controller;

import kz.ualikhan.booktrackers.model.Book;
import kz.ualikhan.booktrackers.model.BookStatus;
import kz.ualikhan.booktrackers.repository.BookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping
    public String listBooks(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "books/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("statuses", BookStatus.values());
        model.addAttribute("formAction", "/books");
        return "books/form";
    }

    @PostMapping
    public String createBook(@ModelAttribute Book book) {
        normalizeBook(book);
        updateStatusByProgress(book);
        bookRepository.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Книга не найдена: " + id));

        model.addAttribute("book", book);
        model.addAttribute("statuses", BookStatus.values());
        model.addAttribute("formAction", "/books/" + id);

        return "books/form";
    }

    @PostMapping("/{id}")
    public String updateBook(@PathVariable Long id, @ModelAttribute Book formBook) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Книга не найдена: " + id));

        book.setTitle(formBook.getTitle());
        book.setAuthor(formBook.getAuthor());
        book.setTotalPages(formBook.getTotalPages());
        book.setPagesRead(formBook.getPagesRead());
        book.setPrice(formBook.getPrice());
        book.setGenre(formBook.getGenre());
        book.setStatus(formBook.getStatus());
        book.setNotes(formBook.getNotes());

        normalizeBook(book);
        updateStatusByProgress(book);
        bookRepository.save(book);

        return "redirect:/books";
    }

    @PostMapping("/{id}/delete")
    public String deleteBook(@PathVariable Long id) {
        bookRepository.deleteById(id);
        return "redirect:/books";
    }

    @PostMapping("/{id}/progress")
    public String updateProgress(@PathVariable Long id, @RequestParam Integer pagesRead) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Книга не найдена: " + id));

        book.setPagesRead(pagesRead);

        normalizeBook(book);
        updateStatusByProgress(book);

        bookRepository.save(book);

        return "redirect:/books";
    }

    private void updateStatusByProgress(Book book) {
        if (book.getPagesRead() == null || book.getPagesRead() == 0) {
            book.setStatus(BookStatus.WANT_TO_READ);
            return;
        }

        if (book.getTotalPages() != null && book.getPagesRead() >= book.getTotalPages()) {
            book.setStatus(BookStatus.FINISHED);
            return;
        }

        book.setStatus(BookStatus.READING);
    }

    private void normalizeBook(Book book) {
        if (book.getPagesRead() == null) {
            book.setPagesRead(0);
        }

        if (book.getTotalPages() != null && book.getPagesRead() > book.getTotalPages()) {
            book.setPagesRead(book.getTotalPages());
        }

        if (book.getPagesRead() < 0) {
            book.setPagesRead(0);
        }
    }
}