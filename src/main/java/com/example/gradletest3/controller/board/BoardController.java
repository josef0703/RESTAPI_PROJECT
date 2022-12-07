package com.example.gradletest3.controller.board;

import com.example.gradletest3.dao.board.BoardDTO;
import com.example.gradletest3.dao.comment.CommentDTO;
import com.example.gradletest3.dao.fileupload.FileUploadDTO;
import com.example.gradletest3.dao.search.Criteria;
import com.example.gradletest3.dao.search.PageMakerDTO;
import com.example.gradletest3.service.board.BoardService;
import com.example.gradletest3.service.comment.CommentService;
import com.example.gradletest3.service.fileupload.FileUploadService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.*;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("board")
@Slf4j
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;
    final FileUploadService fileUploadService;

    public BoardController(BoardService boardService, CommentService commentService, FileUploadService fileUploadService) {
        this.boardService = boardService;
        this.commentService = commentService;
        this.fileUploadService = fileUploadService;
    }

    // 게시판 리스트
    @GetMapping("/boardlist")
    public String boardList(Model model, HttpSession session,
//                            @ModelAttribute("page") PageMakerDTO boardlist,
                            Criteria cri) {
        String returnURL = "";
        System.out.println("들어옴");
        Object user = session.getAttribute("user");
        System.out.println("user =" + user);
        int total = boardService.getTotal();
        System.out.println("total : " + total);
        PageMakerDTO pageMake = new PageMakerDTO(total, cri);
        System.out.println(pageMake.getCri().getAmount());
//        if (user == null) {
//            returnURL = "/user/login";
//        } else {
//        model.addAttribute("boardlist", boardService.boardList(toteal, cri));
            model.addAttribute("pages", pageMake);

            model.addAttribute("boardlist", boardService.boardList(cri));

            log.info(String.valueOf(pageMake.getEndpage()));

            model.addAttribute("maxPage", pageMake.getEndpage());


//        System.out.println(pageMake.getStartPage());
//        System.out.println(pageMake.getCri().getPageNum());
//        System.out.println(pageMake.getCri().getAmount());
            System.out.println("마지막 페이지 : " + pageMake.getEndpage());

            model.addAttribute("user", user);

            returnURL = "/board/boardlist";
//        }



//        }
        return returnURL;
    }

    // 게시판 작성페이지
    @GetMapping("/boardwrite")
    public String boardwrite(HttpSession session, Model model) {
        String returnURL = "";
        Object user = session.getAttribute("user");
        if (user == null) {
            returnURL = "/user/login";
        } else {
        model.addAttribute("user", user);
        returnURL = "/board/boardwrite";
        }


        return returnURL;
    }


    //게시판 작성
    @PostMapping("/boardwrite")
    public String boardwrite(BoardDTO boardDTO) {
        int result = boardService.boardwrite(boardDTO);
        String returnURL = "";
        System.out.println(result);

        if (result == 1) {
            System.out.println("성공");
            returnURL = "redirect:/board/boardlist";
        } else {
            System.out.println("실패");
            returnURL = "redirect:/board/boardwrite";
        }

        return returnURL;
    }

    @GetMapping("/boardone")
    public String boardone(@RequestParam("b_num") int b_num, Model model, HttpServletRequest req, HttpServletResponse res,
                           HttpSession session) {

        BoardDTO b_result = boardService.boardone(b_num);
        List<FileUploadDTO> f_result = fileUploadService.getFileList(b_num);
        List<CommentDTO> comment = commentService.getList(b_num);
        int count = commentService.getCount(b_num);

//        System.out.println(result.getB_num());
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("findbynum", boardService.boardone(b_num));
        model.addAttribute("commentList", comment);
        model.addAttribute("count", count);
        model.addAttribute("fileList", f_result);

        Cookie[] cookies = req.getCookies();

//        Cookie c = new Cookie("visit-cookie", "test");
//        System.out.println(c.getValue());
//        System.out.println(c.getName());
//        res.addCookie(c);

//        for (Cookie cookie : cookies) {
//            cookie.setValue(cookie.getValue() + "_" + req.getParameter("b_num"));
//            System.out.println("쿠키 : " + cookie);
//            System.out.println(cookie.getValue());
//        }

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (!cookie.getValue().contains(req.getParameter("b_num"))) {
                    log.info("if구문");
                    log.info("cookie.getname " + cookie.getName());
                    log.info("cookie.getValue " + cookie.getValue());
                    log.info(String.valueOf(cookie.getValue().contains(req.getParameter("b_num"))));
                    log.info("-----------------------------");
                    cookie.setValue(cookie.getValue() + "_" + req.getParameter("b_num"));
                    cookie.setMaxAge(60 * 60 * 2);
                    res.addCookie(cookie);
                    boardService.boardViewCount(b_num);
                }
            }
        } else {
            Cookie newCookie = new Cookie("visit_cookie", req.getParameter("b_num"));
            log.info("else구문");
            newCookie.setMaxAge(60 * 60 * 2);
            res.addCookie(newCookie);
            boardService.boardViewCount(b_num);
        }




//        System.out.println(req.getParameter("num"));

        //조회수 로직
//        Cookie[] cookies = req.getCookies();
//
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                System.out.println("cookie.getname" + cookie.getName());
//                System.out.println("cookie.getvalue" + cookie.getValue());
//
//                System.out.println("parameter 값 : "+req.getParameter("b_num"));
//
//
//                //세션 value값에 b_num값이 있으면 조회수 안올라감.
//                //세션을 없애고 쿠키로 로그인 확인해야하는지 고민 필요
//                if (!cookie.getValue().contains(req.getParameter("b_num"))) {
//                    cookie.setValue(cookie.getValue() + "_" + req.getParameter("b_num"));
//                    cookie.setMaxAge(60 * 60 * 24);
//                    res.addCookie(cookie);
//                    boardService.boardViewCount(b_num);
//                    System.out.println("변경 후 쿠키값 : "+cookie.getValue());
//
//                }
//            }
//        } else {
//            Cookie newCookie = new Cookie("visit_cookie", req.getParameter("b_num"));
//            newCookie.setMaxAge(60 * 60 * 2);
//            res.addCookie(newCookie);
//            boardService.boardViewCount(b_num);
//        }


        return "/board/boardone";
    }

    @PostMapping("/comment/{b_num}")
    public String commentInsert(@PathVariable("b_num") int b_num, CommentDTO commentDTO, HttpSession session) {

        String return_message = "";

        commentDTO.setR_writer((String) session.getAttribute("user"));

        System.out.println("r_num = " + commentDTO.getR_num());
        System.out.println("b_num = " + commentDTO.getB_num());
        System.out.println("r_writer =" + commentDTO.getR_writer());
        System.out.println("r_content =" + commentDTO.getR_content());

        commentService.commentInsert(commentDTO);

        return "redirect:/board/boardone?b_num=" + b_num;
    }


    @GetMapping("/boardupdate/{b_num}")
    public String boardUpdate(@PathVariable("b_num") int b_num, Model model) {
        model.addAttribute("findbynum", boardService.boardone(b_num));
        return "/board/boardupdate";
    }

    @PostMapping("/boardupdate/{b_num}")
    public String boardUpdateOk(BoardDTO boardDTO, Model model) {

        String returnURL = "";
//        System.out.println(boardDTO.getB_title());
//        System.out.println(boardDTO.getB_content());
        int result = boardService.boardupdate(boardDTO);
        System.out.println(boardDTO.getB_num());
        System.out.println("수정 값 : " + result);

        if (result == 1) {
            returnURL = "redirect:/board/boardwrite";
        } else {
            returnURL = "redirect:/board/boardupdate/" + boardDTO.getB_num();
        }
        return returnURL;
    }

    @GetMapping("/boarddelete/{b_num}")
    public String boarddelete(@PathVariable("b_num") int b_num, Model model) {
        model.addAttribute("findbynum", boardService.boardone(b_num));
        return "/board/boarddelete";
    }

    @PostMapping("/boarddelete/{b_num}")
    public String boarddeleteOk(@PathVariable("b_num") int b_num, Model model) {
        String returnURL = "";
        int result = boardService.boarddelete(b_num);
        System.out.println("result 값 : " + result);

        if (result == 1) {
            returnURL = "redirect:/board/boardlist";
        } else {
            returnURL = "redirect:/board/boardone/" + b_num;
        }
        return returnURL;
    }

    @PostMapping("/upload_ok")
    public String FileUpload(@RequestParam("file") MultipartFile file, @RequestParam("b_num") int b_num, FileUploadDTO fileUploadDTO) {

        //파일명을 얻어낼 수 있는 메서드
        String fileRealName = file.getOriginalFilename();

//        Optional<Object> Extension = Optional.ofNullable(fileRealName)
//                .filter(f -> f.contains("."))
//                .map(f -> f.substring(fileRealName.lastIndexOf(".") + 1, fileRealName.lastIndexOf(".") + 4));
        String Extension = fileRealName.substring(fileRealName.lastIndexOf(".") + 1);

        log.info("확장자 명 : " + Extension);
        log.info("b_num =" + b_num);

        //파일 사이즈
        long size = file.getSize();

        System.out.println("파일명 : " + fileRealName);
        System.out.println("용량크기(Byte)" + size);
//        if (!(Extension.isPresent())) {
//            log.info("확장자가 없는 파일입니다.");
////            return "board/boardone?b_num" + b_num;
//
//        }
//        else {
//            log.info("확장자가 있는 파일입니다");
//            String fileExtension = fileRealName.substring(fileRealName.lastIndexOf("."), fileRealName.length());
        String uploadFolder = "C:\\Josef_study\\Project\\intellij\\gradleTest3\\FileUpload";

        UUID uuid = UUID.randomUUID();
        System.out.println(uuid.toString());
        String[] uuids = uuid.toString().split("-");

        String uniqueName = uuids[0];
        System.out.println("생성된 고유문자열" + uniqueName);
        System.out.println("확장자명" + Extension);

        fileUploadDTO.setOrg_file_name(fileRealName);
        fileUploadDTO.setStored_file_name(uniqueName);
        fileUploadDTO.setF_size((int) size);
        fileUploadDTO.setB_num(b_num);
        fileUploadDTO.setF_extension(Extension);

        fileUploadService.FileUpload(fileUploadDTO);
        log.info(fileUploadDTO.getOrg_file_name());
        log.info(fileUploadDTO.getStored_file_name());
        log.info(String.valueOf(fileUploadDTO.getB_num()));
        log.info(String.valueOf(fileUploadDTO.getF_size()));

        File saveFile = new File(uploadFolder + "\\" + uniqueName + "." + Extension);

        try {
            file.transferTo(saveFile);

        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "board/boardlist";

//        }

        //서버에 저장할 파일이름 fileextension으로 .jsp 이런식의 학장자 명을 구함


//        return "board/boardlist";
    }

    @GetMapping("/download")
    public String FileDownload(@RequestParam("f_num") int f_num, FileUploadDTO fileUploadDTO, HttpServletRequest req, HttpServletResponse res) throws IOException {

        FileUploadDTO file = fileUploadService.getdownloadFile(f_num);
        String FileName = file.getStored_file_name();
        String FileExtension = file.getF_extension();
        String FileOrgName = file.getOrg_file_name();

        log.info(FileExtension);

        String FilePath = "C:\\Josef_study\\Project\\intellij\\gradleTest3\\FileUpload\\" + FileName + "." + FileExtension;

        byte[] fileByte = FileUtils.readFileToByteArray(new File(FilePath));

        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(FileName + "." + FileExtension, "UTF-8") + "\";");
        res.setHeader("Content-Transfer-Encoding", "binary");

        res.getOutputStream().write(fileByte);
        res.getOutputStream().flush();
        res.getOutputStream().close();

//        String tempFileName = FilePath + "\\" + FileName;
//
//        StringBuilder sb = new StringBuilder(tempFileName);
//
//        String contentType = "text/plain";
//
//        String saveFileName = sb.toString();
//
//        File file1 = new File(saveFileName);
//        long fileLength = file1.length();
//
//        res.setHeader("Content-Disposition", "attachment; filename=\"" + FileName + "\";");
//        res.setHeader("Content-Transfer-Encoding", "binary");
//        res.setHeader("Content-Type", contentType);
//        res.setHeader("Content-Length", "" + fileLength);
//        res.setHeader("Pragma", "no-cache;");
//        res.setHeader("Expires", "-1;");
//
//        try (FileInputStream fis = new FileInputStream(saveFileName); OutputStream out = res.getOutputStream();) {
//            int readCount = 0;
//            byte[] buffer = new byte[1024];
//            while ((readCount = fis.read(buffer)) != -1) {
//                out.write(buffer, 0, readCount);
//            }
//        } catch (Exception e) {
//            throw new RuntimeException("file Load Error");
//        }


        return "board/boardlist";
    }

    @GetMapping("/fileDelete")
    public String fileDelete(@RequestParam int f_num) {
        FileUploadDTO result = fileUploadService.getdownloadFile(f_num);
        String stored_file_name = result.getStored_file_name();
        String Extension = result.getF_extension();
        log.info(stored_file_name);

        String path = "C:\\Josef_study\\Project\\intellij\\gradleTest3\\FileUpload\\" + stored_file_name + "." + Extension;
        log.info(path);


        File file = new File(path);
        if (file.exists()) {
            if (file.delete()) {
                fileUploadService.fileDelete(f_num);
                log.info("파일 삭제 성공");

            } else {
                log.info("파일 삭제 실패");

            }
        } else {
            log.info("파일이 존재하지 않습니다");
        }


        return "board/boardlist";
    }
}
