package com.myezen.myapp.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myezen.myapp.domain.BoardVo;
import com.myezen.myapp.domain.SearchCriteria;
import com.myezen.myapp.persistance.BoardService_Mapper;


@Service("boardServiceImpl") //이 어노테이션이 없으면 객체생성이 되지않음.
public class BoardServiceImpl implements BoardService{
	
	
	private BoardService_Mapper bsm;
	
	@Autowired
	public BoardServiceImpl(SqlSession sqlSession) {
		
		this.bsm = sqlSession.getMapper(BoardService_Mapper.class);
	
	}
	
	
	
	@Override
	public ArrayList<BoardVo> boardSelectAll(SearchCriteria scri) {
		
		ArrayList<BoardVo> blist = bsm.boardSelectAll(scri);
		
		return blist;
	}



	@Override
	public int boardTotal(SearchCriteria scri) {
		
		int value = bsm.boardTotal(scri);
		
		return value;
	}


	@Override
	public int boardViewCnt(int bidx) {
		
		int value = bsm.boardViewCnt(bidx);
		
		return value;
	}

	
	
	@Override
	public BoardVo boardSelectOne(int bidx) {
		
		BoardVo bv = bsm.boardSelectOne(bidx);
		
		return bv;
	}



	@Override
	public int boardInsert(BoardVo bv) {
		
		int value = bsm.boardInsert(bv);
		
		return value;
	}



	@Override
	public int boardModify(BoardVo bv) {
		
		int value = bsm.boardModify(bv);
		
		return value;
	}



	@Override
	public int boardDelete(BoardVo bv) {
		
		int value = bsm.boardDelete(bv);
		
		return value;
	}


	@Transactional	//답글기능에는 2개의 쿼리가 전부 실행되어야한다. 트랜잭션 기능이 필요
	@Override
	public int boardReply(BoardVo bv) {
		
		//HashMap 사용해보자
		HashMap<String,Integer> hm = new HashMap<String,Integer>();
		hm.put("originbidx", bv.getOriginbidx());
		hm.put("depth", bv.getDepth());
		
		bsm.boardReplyUpdate(hm);
		int value = bsm.boardReplyInsert(bv);
		
		return value;
	}




}
