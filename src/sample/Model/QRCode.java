package sample.Model;
/*
Code credits to https://www.callicoder.com/generate-qr-code-in-java-using-zxing/
Author: Rajeev Singh
Article published: July 19 2017
GitHub link: https://github.com/callicoder/qr-code-generator-and-reader
 */

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public abstract class QRCode {
    protected int width;
    protected int height;

    protected QRCode(int width, int height){
        this.width = width;
        this.height = height;
    }

    protected void generateQRCodeImage(String text, String filename)
            throws WriterException, IOException{
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        Path path = FileSystems.getDefault().getPath(filename);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }

    protected String decodeQRCode(File qrCodeimage) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(qrCodeimage);
        LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        try {
            Result result = new MultiFormatReader().decode(bitmap);
            return result.getText();
        } catch (NotFoundException e) {
            Alerts.errorAlert("There is no QR code in the image!","Please choose the correct file!");
            return null;
        }
    }

}
