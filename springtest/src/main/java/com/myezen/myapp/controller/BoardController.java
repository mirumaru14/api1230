package com.myezen.myapp.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.myezen.myapp.domain.BoardVo;
import com.myezen.myapp.domain.PageMaker;
import com.myezen.myapp.domain.SearchCriteria;
import com.myezen.myapp.service.BoardService;
import com.myezen.myapp.util.MediaUtils;
import com.myezen.myapp.util.UploadFileUtiles;

@Controller
@RequestMapping(value="/board")
public class BoardController {
	
		
	@Autowired		
	BoardService bs;
	
	@Autowired(required=false)		//주입을 받지 않아도 되게끔.
	PageMaker pm;					//페이지메이커 주입
	
	@Resource(name="uploadPath")	//타입으로 찾는 Autowired와 달리 Resource는 name으로 찾는다
	String uploadPath;
	
	@RequestMapping(value="/boardList.do")
	public String boardList(
			SearchCriteria scri,
			Model model) {
		
		
		int totalCount = bs.boardTotal(scri);		
		pm.setScri(scri);
		pm.setTotalCount(totalCount);
		
		
		ArrayList<BoardVo> blist = bs.boardSelectAll(scri);
		
		model.addAttribute("blist", blist);
		model.addAttribute("pm", pm);
		
		return "board/boardList";
	}
	
	@RequestMapping(value="/boardContents.do")
	public String boardContents(@RequestParam("bidx") int bidx, Model model) {
		
		
		int value = bs.boardViewCnt(bidx);
		BoardVo bv = bs.boardSelectOne(bidx);
		
		model.addAttribute("bv", bv);
		
		return "board/boardContents";
	}
	
	@RequestMapping(value="/boardWrite.do")
	public String boardWrite() {
		
		
		return "board/boardWrite";
	}
	
	@RequestMapping(value="/boardWriteAction.do")
	public String boardWriteAction(
			@RequestParam("subject") String subject,
			@RequestParam("contents") String contents,
			@RequestParam("writer") String writer,
			@RequestParam("pwd") String pwd,
			@RequestParam("filename") MultipartFile filename,
			HttpSession session) throws Exception {
		
		System.out.println("subject:"+subject);
		MultipartFile file = filename;
		System.out.println("원본파일이름 :"+file.getOriginalFilename());
		
		//업로드된 파일이름 초기화
		String uploadedFileName ="";
		
		//원본파일이름이 있을 시(파일이 있을 시)
		if(!file.getOriginalFilename().equals("")) {
			uploadedFileName = UploadFileUtiles.uploadFile(	//파일 저장하는 메소드 UploadFileUtiles를 보자. 주석많음.
					uploadPath, 
					file.getOriginalFilename(), 
					file.getBytes());
			//util폴더 클래스파일에 uploadFile메소드 호출. 매개변수는 경로, 파일이름, 파일크기
			
		}
		
		//이 ip는 클라이언트의 ip가 아닌 서버ip이다. 클라이언트ip는 검색을통해서 알아보자
		String ip = InetAddress.getLocalHost().getHostAddress();
		
		//세션의 midx를 대입
		int midx=0;
		if(session.getAttribute("midx")!=null) {
			midx = (int)session.getAttribute("midx");
			//midx = Integer.parseInt(session.getAttribute("midx").toString());
		}
		
		BoardVo bv = new BoardVo();
		bv.setSubject(subject);
		bv.setContents(contents);
		bv.setWriter(writer);
		bv.setIp(ip);
		bv.setMidx(midx);
		bv.setPwd(pwd);
		bv.setFilename(uploadedFileName);
		
		System.out.println("subject:"+bv.getSubject());
		
		int value = bs.boardInsert(bv);
		
		return "redirect:/board/boardList.do";
	}
	
	//다른위치에 있는 파일도 다운로드 시킬수 있는 메소드
	@ResponseBody
	@RequestMapping(value="/displayFile.do", method=RequestMethod.GET)
	public ResponseEntity<byte[]> displayFile(String fileName,@RequestParam(value="down",defaultValue="0" ) int down ) throws Exception{
		
	//	System.out.println("fileName:"+fileName);
		
		InputStream in = null;		
		ResponseEntity<byte[]> entity = null;
		
	//	logger.info("FILE NAME :"+fileName);
		
		try{
			String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
			MediaType mType = MediaUtils.getMediaType(formatName);
			
			HttpHeaders headers = new HttpHeaders();		
			 
			in = new FileInputStream(uploadPath+fileName);
			
			
			if(mType != null){
				
				if (down==1) {	//boardcontents에서 파일다운로드 영역의 링크는 down=1을 받아오게 되어있음.
					//down=1 값을 받아오게되면 아래 메소드를 통해 다운로드가 실행됨
					fileName = fileName.substring(fileName.indexOf("_")+1);
					headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
					headers.add("Content-Disposition", "attachment; filename=\""+
							new String(fileName.getBytes("UTF-8"),"ISO-8859-1")+"\"");	
					
				}else {
					headers.setContentType(mType);	
				}
				
			}else{
				
				fileName = fileName.substring(fileName.indexOf("_")+1);
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				headers.add("Content-Disposition", "attachment; filename=\""+
						new String(fileName.getBytes("UTF-8"),"ISO-8859-1")+"\"");				
			}
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in),
					headers,
					HttpStatus.CREATED);
			
		}catch(Exception e){
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		}finally{
			in.close();
		}
		return entity;
	} 
	
	@RequestMapping(value="/boardModify.do")
	public String boardModify(@RequestParam("bidx") int bidx,Model model) {
		
		BoardVo bv = bs.boardSelectOne(bidx);
		
		model.addAttribute("bv", bv);
		
		return "board/boardModify";
	}
	
	@RequestMapping(value="/boardModifyAction.do")
	public String boardModifyAction(
			@RequestParam("bidx") int bidx,
			@RequestParam("subject") String subject,
			@RequestParam("contents") String contents,
			@RequestParam("writer") String writer,
			@RequestParam("pwd") String pwd,
			@RequestParam("filename") MultipartFile filename,
			HttpSession session) throws Exception {
		
		System.out.println("subject:"+subject);
		MultipartFile file = filename;
		System.out.println("원본파일이름 :"+file.getOriginalFilename());
		
		//업로드된 파일이름 초기화
		String uploadedFileName ="";
		
		//원본파일이름이 있을 시(파일이 있을 시)
		if(!file.getOriginalFilename().equals("")) {
			uploadedFileName = UploadFileUtiles.uploadFile(	//파일 저장하는 메소드 UploadFileUtiles를 보자. 주석많음.
					uploadPath, 
					file.getOriginalFilename(), 
					file.getBytes());
			//util폴더 클래스파일에 uploadFile메소드 호출. 매개변수는 경로, 파일이름, 파일크기
			
		}
		
		//이 ip는 클라이언트의 ip가 아닌 서버ip이다. 클라이언트ip는 검색을통해서 알아보자
		String ip = InetAddress.getLocalHost().getHostAddress();
		
		int midx=0;
		if(session.getAttribute("midx")!=null) {
			midx = (int)session.getAttribute("midx");
			//midx = Integer.parseInt(session.getAttribute("midx").toString());
		}
		
		
		
		BoardVo bv = new BoardVo();
		bv.setBidx(bidx);
		bv.setMidx(midx);
		bv.setSubject(subject);
		bv.setContents(contents);
		bv.setWriter(writer);
		bv.setIp(ip);
		bv.setPwd(pwd);
		bv.setFilename(uploadedFileName);
		
		System.out.println("subject:"+bv.getSubject());
		
		int value = bs.boardModify(bv);
		
		String path ="";
		if(value==1) {
			path = "redirect:/board/boardContents.do?bidx="+bidx;			
		}else {		
			path = "redirect:/board/boardModify.do?bidx="+bidx;
		}
		return path;
	}
	
	@RequestMapping(value="/boardDelete.do")
	public String boardDelete(@RequestParam("bidx") int bidx,Model model) {
		
		BoardVo bv = bs.boardSelectOne(bidx);
		
		model.addAttribute("bv", bv);
				
		
		return "board/boardDelete";
	}
	
	@RequestMapping(value="/boardDeleteAction.do")
	public String boardDeleteAction(
			@RequestParam("bidx") int bidx,
			@RequestParam("pwd") String pwd) {
		
		BoardVo bv = new BoardVo();
		bv.setBidx(bidx);
		bv.setPwd(pwd);
		
		int value = bs.boardDelete(bv);
		String path="";
		if(value==1) {
			path="redirect:/board/boardList.do";		
		}else {
			path="redirect:/board/boardDelete.do?bidx="+bidx;
		}
		
		return path;
	}
	
	@RequestMapping(value="/boardReply.do", method=RequestMethod.GET)
	public String boardReply(
			@RequestParam("bidx") int bidx,
			@RequestParam("originbidx") int originbidx,
			@RequestParam("depth") int depth,
			@RequestParam("level_") int level_,
			Model model) {
		
		BoardVo bv = new BoardVo();
		bv.setBidx(bidx);
		bv.setOriginbidx(originbidx);
		bv.setDepth(depth);
		bv.setLevel_(level_);
		
		model.addAttribute("bv", bv);
				
		return "board/boardReply";
	}
	
	@RequestMapping(value="/boardReplyAction.do", method=RequestMethod.POST)
	public String boardReplyAction(
			@RequestParam("bidx") int bidx,
			@RequestParam("originbidx") int originbidx,
			@RequestParam("depth") int depth,
			@RequestParam("level_") int level_,
			@RequestParam("subject") String subject,
			@RequestParam("contents") String contents,
			@RequestParam("writer") String writer,
			@RequestParam("pwd") String pwd,
			@RequestParam("filename") MultipartFile filename,
			HttpSession session,
			Model model) throws Exception {
		
		
		MultipartFile file = filename;
		System.out.println("원본파일이름 :"+file.getOriginalFilename());
		
		//업로드된 파일이름 초기화
		String uploadedFileName ="";
		
		//원본파일이름이 있을 시(파일이 있을 시)
		if(!file.getOriginalFilename().equals("")) {
			uploadedFileName = UploadFileUtiles.uploadFile(	//파일 저장하는 메소드 UploadFileUtiles를 보자. 주석많음.
					uploadPath, 
					file.getOriginalFilename(), 
					file.getBytes());
			//util폴더 클래스파일에 uploadFile메소드 호출. 매개변수는 경로, 파일이름, 파일크기
			
		}
		
		//이 ip는 클라이언트의 ip가 아닌 서버ip이다. 클라이언트ip는 검색을통해서 알아보자
		String ip = InetAddress.getLocalHost().getHostAddress();
		
		int midx=0;
		if(session.getAttribute("midx")!=null) {
			midx = (int)session.getAttribute("midx");
			//midx = Integer.parseInt(session.getAttribute("midx").toString());
		}
		
		
		
		BoardVo bv = new BoardVo();		
		bv.setMidx(midx);
		bv.setSubject(subject);
		bv.setOriginbidx(originbidx);
		bv.setDepth(depth);
		bv.setLevel_(level_);
		bv.setContents(contents);
		bv.setWriter(writer);
		bv.setIp(ip);
		bv.setPwd(pwd);
		bv.setFilename(uploadedFileName);
				
		
		int value = bs.boardReply(bv);
		
		return "redirect:/board/boardList.do";
	}
	
}
