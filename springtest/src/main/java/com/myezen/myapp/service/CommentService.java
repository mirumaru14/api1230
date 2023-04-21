package com.myezen.myapp.service;

import java.util.ArrayList;

import com.myezen.myapp.domain.CommentVo;

public interface CommentService {
	
	public int commentInsert(CommentVo cv);
	
	public ArrayList<CommentVo> commentSelectAll(int bidx, int nextBlock);
	
	public int commentTotalCnt(int bidx);
	
	public int commentDelete(int bidx, int cidx);

}
