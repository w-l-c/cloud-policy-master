package cn.rebornauto.platform.common.servlets;
import cn.rebornauto.platform.common.Const;
import javax.imageio.ImageIO;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

@WebServlet(urlPatterns = {"/sys/vcode"})
public class SafeCodeServlet extends HttpServlet {
    //产生随即的字体
    private Font getFont() {
        Random random = new Random();
        Font font[] = new Font[5];
        font[0] = new Font("Ravie", Font.PLAIN, 24);
        font[1] = new Font("Antique Olive Compact", Font.PLAIN, 24);
        font[2] = new Font("Forte", Font.PLAIN, 24);
        font[3] = new Font("Wide Latin", Font.PLAIN, 24);
        font[4] = new Font("Gill Sans Ultra Bold", Font.PLAIN, 24);
        return font[random.nextInt(5)];
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 设置响应头 Content-type类型
        resp.setContentType("image/jpeg");
        // 以下三句是用于设置页面不缓存
        resp.setHeader("Pragma", "No-cache");
        resp.setHeader("Cache-Control", "No-cache");
        resp.setDateHeader("Expires", 0);
        OutputStream os = resp.getOutputStream();
        int width = 83;
        int height = 30;
        try {
            //获取长度宽度
            String strw = req.getParameter("w");
            String strh = req.getParameter("h");
            if (strw != null && "".equals(strw)) {
                width = Integer.parseInt(strw);
            }
            if (strh != null && "".equals(strh)) {
                height = Integer.parseInt(strh);
            }
        } catch (Exception e) {
        }

        // 建立指定宽、高和BufferedImage对象
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics(); // 该画笔画在image上
        Color c = g.getColor(); // 保存当前画笔的颜色，用完画笔后要回复现场
        g.fillRect(0, 0, width, height);
        char[] ch = "abcdefghjkmnpqrstuvwxyz23456789".toCharArray(); // 随即产生的字符串
        int length = ch.length; // 随即字符串的长度
        String sRand = ""; // 保存随即产生的字符串
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            // 设置字体
            g.setFont(getFont());
            // 随即生成0-9的数字
            String rand = new Character(ch[random.nextInt(length)]).toString();
            sRand += rand;
            // 设置随机颜色
            g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
            g.drawString(rand, 20 * i + 6, 25);
        }
        //产生随即干扰点
        for (int i = 0; i < 20; i++) {
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            g.drawOval(x1, y1, 2, 2);
        }
        g.setColor(c); // 将画笔的颜色再设置回去
        g.dispose();

        //将验证码记录到session
        req.getSession().setAttribute(Const.SafeCode_SessionName, sRand);
        // 输出图像到页面
        ImageIO.write(image, "JPEG", os);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)throws  IOException {
        doGet(req, resp);
    }

}
