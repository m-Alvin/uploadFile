package cn.demo;
/**
 * struts2 ʵ���ļ��ϴ�
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
	private File myFile; //���������������(eg:xxx)
	private String myFileFileName; //�������������File����������һ��,��Ϊ:xxxFileName
	private String myFileContentType; //�������������File����������һ��,��Ϊ:xxxContentType

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
		//ʵ�����ϴ��ļ�������������
		InputStream is = new FileInputStream(myFile);  

		// �����ϴ��ļ�Ŀ¼  
        String uploadPath = ServletActionContext.getServletContext()  
                .getRealPath("/upload");  
        
        //�ļ�������,�õ�ǰʱ����Ϊ�ļ���
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String time = sdf.format(date);
        
        String fileName = this.getMyFileFileName();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        String newFileName = time + suffix;
        
        // ����Ŀ���ļ�  
        File toFile = new File(uploadPath, newFileName);
        
        //����ļ���Ŀ¼������,�ʹ�����Ŀ¼.
        if(!toFile.getParentFile().exists()){
        	toFile.getParentFile().mkdirs();
        }
        
        // ����һ�������  
        OutputStream os = new FileOutputStream(toFile);  
  
        //���û���  
        byte[] buffer = new byte[1024];  
  
        int length = 0;  
  
        //��ȡupload�ļ������toFile�ļ���  
        while (-1 != (length = is.read(buffer))) {  
            os.write(buffer, 0, length);  
            os.flush();
        }  
        
        os.close();
        is.close();  
		return SUCCESS;
	}
}
