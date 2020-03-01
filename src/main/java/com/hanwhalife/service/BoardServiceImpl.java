package com.hanwhalife.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanwhalife.entity.Board;
import com.hanwhalife.repository.UserRepository;


@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private UserRepository boardRepository;

	@Override
	public void insertBoard(Board board) {
	}

	@Override
	public void updateBoard(Board board) {
	}

	@Override
	public void deleteBoard(Board board) {
	}

	@Override
	public Board getBoard() {
		
		return null;
	}

}
