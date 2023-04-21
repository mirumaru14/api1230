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
	
	//��۱�ɿ��� 2���� ������ �۵��ؾ��Ѵ�. �׷��Ƿ� mapper�� �޼ҵ� ���� 2���� �ۼ��Ѵ�.
	//update�������� originbidx�� depth�� �޴� ������
	//public int boardReplyUpdate(int originbidx, int depth);
	
	public int boardReplyUpdate(HashMap<String,Integer> hm);
	
	public int boardReplyInsert(BoardVo bv);
}
