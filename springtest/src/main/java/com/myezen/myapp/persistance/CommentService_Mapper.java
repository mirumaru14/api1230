package com.myezen.myapp.persistance;

import java.util.ArrayList;

import com.myezen.myapp.domain.CommentVo;

public interface CommentService_Mapper {
	
	public int commentInsert(CommentVo cv);
	
	public ArrayList<CommentVo> commentSelectAll(int bidx, int nextBlock);
	
	public int commentTotalCnt(int bidx);
	
	public int commentDelete(int bidx, int cidx);
}
