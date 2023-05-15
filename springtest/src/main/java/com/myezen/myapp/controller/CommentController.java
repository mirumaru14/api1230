package com.myezen.myapp.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myezen.myapp.domain.CommentVo;
import com.myezen.myapp.service.CommentService;

@RestController		//@Controller와 @ResponseBody를 포함
@RequestMapping(value="/comment")
public class CommentController {
	
	@Autowired
	private CommentService cs;
	
	
	@RequestMapping(value="/commentWrite.do", method=RequestMethod.POST)
	public JSONObject commentWrite(CommentVo cv) throws Exception{
		
		
		String ip = InetAddress.getLocalHost().getHostAddress();
		cv.setIp(ip);
		
		
		int value = cs.commentInsert(cv);
		System.out.println("입력여부"+value);
		
		JSONObject js = new JSONObject();
		js.put("value", value);
		
		return js;
	}
	
	@RequestMapping(value="/{bidx}/{nextBlock}/commentList.do", method=RequestMethod.GET)
	public JSONObject commentList(
			@PathVariable("bidx") int bidx,	//url에 있는 bidx를 추출
			@PathVariable("nextBlock") int nextBlock
			) {
		
		JSONObject js = new JSONObject();		
		ArrayList<CommentVo> alist = cs.commentSelectAll(bidx, nextBlock);	
		int totalCnt = cs.commentTotalCnt(bidx);	//게시글에 달린 댓글의 총 개수
		
		String moreView = "N";		//더보기버튼 보이는 여부
		
		if(totalCnt > nextBlock*15) {
			moreView = "Y";			//댓글이 15개 이상이면 더보기버튼 활성화
		}
		
		js.put("alist", alist);
		js.put("moreView", moreView);
		
		return js;
	}
	
	@RequestMapping(value="/{bidx}/{nextBlock}/more.do", method=RequestMethod.GET)
	public JSONObject more(
			@PathVariable("bidx") int bidx,	
			@PathVariable("nextBlock") int nextBlock	//이 경로로는 nextBlock이 처음엔 2로 넘어옴.		
			) {
				
		JSONObject js = new JSONObject();
		int totalCnt = cs.commentTotalCnt(bidx);
		int nextBlock2 = 0;
		
		if(totalCnt>nextBlock*15) {
			nextBlock2 = nextBlock+1;
		}else {
			nextBlock2 = nextBlock;
		}
		System.out.println(nextBlock2);
		ArrayList<CommentVo> alist = cs.commentSelectAll(bidx, nextBlock2);
		
		js.put("alist", alist);
		js.put("nextBlock", nextBlock2);
				
		return js;
	}
	
	@RequestMapping(value="/{bidx}/{cidx}/commentDelete.do", method=RequestMethod.GET)
	public JSONObject commentDelete(
			@PathVariable("bidx") int bidx,
			@PathVariable("cidx") int cidx
			) {
		
		JSONObject js = new JSONObject();
		int value = cs.commentDelete(bidx, cidx);
		
		js.put("value", value);
		
		return js;
	}
	
	
}
