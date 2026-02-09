package cms.admin.board.service;

import cms.admin.board.repository.BoardRepository;
import cms.entity.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    public Board findById(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found"));
    }

    @Transactional
    public Board save(Board board) {
        if (board.getPostYn() == null) {
            board.setPostYn(Boolean.TRUE);
        }
        if (board.getUseYn() == null) {
            board.setUseYn(Boolean.TRUE);
        }
        if (board.getCategoryYn() == null) {
            board.setCategoryYn(Boolean.TRUE);
        }
        return boardRepository.save(board);
    }

    @Transactional
    public void delete(Long id) {
        boardRepository.deleteById(id);
    }
}
