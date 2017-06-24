#ifndef BOARD
#define BOARD

#include <cstring>

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
	{ return !memcmp(this, &x, sizeof(x)); }
};

template <>
struct std::hash<pos> : public unary_function<pos, size_t> {
	size_t operator()(const pos& x) const {
		return x.x * 8 + x.y;
	}
};

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
};

class board {
private:
	std::unordered_map<pos, chess> chess_map;

public:
};

#endif // BOARD
