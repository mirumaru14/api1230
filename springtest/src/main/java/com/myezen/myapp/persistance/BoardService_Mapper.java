package com.myezen.myapp.persistance;

import java.util.ArrayList;
import java.util.HashMap;

import com.myezen.myapp.domain.BoardVo;
import com.myezen.myapp.domain.SearchCriteria;

public interface BoardService_Mapper {

	public ArrayList<BoardVo> boardSelectAll(SearchCriteria scri);
	
	public int boardTotal(SearchCriteria scri);	
	
	public int boardViewCnt(int bidx);
	
	public BoardVo boardSelectOne(int bidx);
	
	public int boardInsert(BoardVo bv);
	
	public int boardModify(BoardVo bv);
	
	public int boardDelete(BoardVo bv);
	
	//답글기능에는 2개의 쿼리가 작동해야한다. 그러므로 mapper의 메소드 또한 2개를 작성한다.
	//update쿼리에는 originbidx와 depth를 받는 쿼리문
	//public int boardReplyUpdate(int originbidx, int depth);
	
	public int boardReplyUpdate(HashMap<String,Integer> hm);
	
	public int boardReplyInsert(BoardVo bv);
}
