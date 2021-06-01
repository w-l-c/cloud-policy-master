package cn.rebornauto.platform.utils;

/**
 * Title: Description: Company:
 * 
 * @author kgc
 * @date Mar 4, 2020 2:11:11 PM
 */
public class UrlUtil {

	public static String getUrl(String url) throws Exception {
		java.net.URL urls = new java.net.URL(url);
		String host = urls.getHost();// 获取主机名
		return host;// 结果 blog.csdn.net
	}

	public static void main(String[] args) {
		String url = "https://testfile.rebornauto.cn/aliyunNAS/if/202003/03/502c04aa50d048ecbf6eb0472f4d654e.jpg";
		String domain = "";
		try {
			domain = getUrl(url);
			String pic = url.substring(domain.length()+8, url.length());
			System.out.println("pic=" + pic);
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}
}
