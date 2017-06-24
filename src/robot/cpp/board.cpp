#ifndef BOARD
#define BOARD

#include <unordered_map>

enum chess_type {
	king,
	queen,
	pope,
	knight,
	castle,
	pawn
};

struct pos {
	int x, y;

	pos() = default;
	pos(const pos&) = default;

	pos(int x, int y) : x(x), y(y) {}

	bool operator==(const pos& x) const
	{ return !memcpy(this, &x, sizeof(x)); }
}

class chess {
private:
	pos at;
	chess_type type;
	bool side;
	bool moved;

public:
	chess() = default;
	chess(const chess&) = default;

	chess(const pos& at, chess_type type, bool side, bool moved) :
	      at(at), type(type), side(side), moved(moved) {}
}

class board {
private:
	unordered_map<pos, chess> chess_map;

public:
};

#endif // BOARD
