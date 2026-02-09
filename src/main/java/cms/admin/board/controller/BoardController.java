package cms.admin.board.controller;

import cms.admin.board.service.BoardService;
import cms.entity.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("pageTitle", "Board Management");
        model.addAttribute("boards", boardService.findAll());
        return "admin/board/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("pageTitle", "Create Board");
        model.addAttribute("board", new Board());
        return "admin/board/form";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Board board) {
        boardService.save(board);
        return "redirect:/admin/boards";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("pageTitle", "Edit Board");
        model.addAttribute("board", boardService.findById(id));
        return "admin/board/form";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id, @ModelAttribute Board board) {
        board.setBoardId(id);
        boardService.save(board);
        return "redirect:/admin/boards";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        boardService.delete(id);
        return "redirect:/admin/boards";
    }
}
