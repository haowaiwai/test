// 下列 ifdef 块是创建使从 DLL 导出更简单的
// 宏的标准方法。此 DLL 中的所有文件都是用命令行上定义的 DECODER_DLL_EXPORTS
// 符号编译的。在使用此 DLL 的
// 任何其他项目上不应定义此符号。这样，源文件中包含此文件的任何其他项目都会将
// DECODER_DLL_API 函数视为是从 DLL 导入的，而此 DLL 则将用此宏定义的
// 符号视为是被导出的。
#ifdef DECODER_DLL_EXPORTS
#define DECODER_DLL_API __declspec(dllexport)
#else
#define DECODER_DLL_API __declspec(dllimport)
#endif

// "sSrcFile"（输入参数） : 待解密的文件(full path and name) 
// "sDstFile"（输入参数） : 解密结果文件(full path and name)
// "szMima[8]"（输入参数） : 密码长度为 8 个字节！！！
// 返回值 : 0 代表成功
//
DECODER_DLL_API int DecodeFile(const char *sSrcFile, const char *sDstFile, const unsigned char szMima[8]);


/*
// 此类是从 decoder_dll.dll 导出的
class DECODER_DLL_API Cdecoder_dll {
public:
	Cdecoder_dll(void);
	// TODO: 在此添加您的方法。
};

extern DECODER_DLL_API int ndecoder_dll;

DECODER_DLL_API int fndecoder_dll(void);
*/