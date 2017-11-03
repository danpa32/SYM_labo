package ch.heigvd.sym.sym_labo2.Utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;

/**
 * https://github.com/yutax77/BenchOpenBitSetComp/blob/090fe70933befb30bdbf06cdacce26f1f455f638/src/main/java/com/yutax77/BenchBitmapComp/ZlibArchiver.java
 */
public class CompressRequest {
    public static byte[] compress(byte[] input) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Deflater comp = new Deflater();
        comp.setLevel(Deflater.BEST_COMPRESSION);

        DeflaterOutputStream dos = new DeflaterOutputStream(out, comp);

        dos.write(input);
        dos.finish();
        return out.toByteArray();
    }
}
