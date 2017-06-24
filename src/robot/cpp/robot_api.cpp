#ifdef WIN32
__declspec(dllexport) char* calculateMove(char* board, int depth);
#endif

char* calculateMove(char* board, int depth)
{
	return "{}";
}
