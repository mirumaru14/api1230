package com.myezen.myapp.service;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myezen.myapp.domain.CommentVo;
import com.myezen.myapp.persistance.CommentService_Mapper;

@Service("CommentServiceImpl")
public class CommentServiceImpl implements CommentService{
	
private CommentService_Mapper csm;
	
	@Autowired
	public CommentServiceImpl(SqlSession sqlSession) {
		this.csm = sqlSession.getMapper(CommentService_Mapper.class);
	}


	@Override
	public int commentInsert(CommentVo cv) {
		
		int value = csm.commentInsert(cv);
		
		return value;
	}


	@Override
	public ArrayList<CommentVo> commentSelectAll(int bidx, int nextBlock) {
		
		ArrayList<CommentVo> alist = csm.commentSelectAll(bidx, nextBlock);
		
		return alist;
	}


	@Override
	public int commentTotalCnt(int bidx) {
		
		int value = csm.commentTotalCnt(bidx);
		
		return value;
	}


	@Override
	public int commentDelete(int bidx, int cidx) {
		
		int value = csm.commentDelete(bidx, cidx);
		
		return value;
	}

}
