package cn.demo;
/**
 * struts2 实现文件上传
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class UploadAction extends ActionSupport{
	private File myFile; //该属性名是任意的(eg:xxx)
	private String myFileFileName; //该属性名必须和File属性名保持一致,需为:xxxFileName
	private String myFileContentType; //该属性名必须和File属性名保持一致,需为:xxxContentType

	public File getMyFile() {
		return myFile;
	}

	public void setMyFile(File myFile) {
		this.myFile = myFile;
	}

	public String getMyFileFileName() {
		return myFileFileName;
	}

	public void setMyFileFileName(String myFileFileName) {
		this.myFileFileName = myFileFileName;
	}

	public String getMyFileContentType() {
		return myFileContentType;
	}

	public void setMyFileContentType(String myFileContentType) {
		this.myFileContentType = myFileContentType;
	}

	public String execute() throws Exception{
		//实例化上传文件的输入流对象
		InputStream is = new FileInputStream(myFile);  

		// 设置上传文件目录  
        String uploadPath = ServletActionContext.getServletContext()  
                .getRealPath("/upload");  
        
        //文件重命名,用当前时间作为文件名
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String time = sdf.format(date);
        
        String fileName = this.getMyFileFileName();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        String newFileName = time + suffix;
        
        // 设置目标文件  
        File toFile = new File(uploadPath, newFileName);
        
        //如果文件父目录不存在,就创建父目录.
        if(!toFile.getParentFile().exists()){
        	toFile.getParentFile().mkdirs();
        }
        
        // 创建一个输出流  
        OutputStream os = new FileOutputStream(toFile);  
  
        //设置缓存  
        byte[] buffer = new byte[1024];  
  
        int length = 0;  
  
        //读取upload文件输出到toFile文件中  
        while (-1 != (length = is.read(buffer))) {  
            os.write(buffer, 0, length);  
            os.flush();
        }  
        
        os.close();
        is.close();  
		return SUCCESS;
	}
}
