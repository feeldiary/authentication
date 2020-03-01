package com.hanwhalife.service;

import com.hanwhalife.entity.Board;

public interface BoardService {

	void insertBoard(Board board);

	void updateBoard(Board bo0ard);

	void deleteBoard(Board board);

	Board getBoard();

}