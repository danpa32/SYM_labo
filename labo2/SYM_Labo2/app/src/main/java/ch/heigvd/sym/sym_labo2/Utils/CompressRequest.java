package ch.heigvd.sym.sym_labo2.Utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

public class CompressRequest {
    public static byte[] compress(byte[] input) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        // Support GZIP and PKZIP compression format instead of ZLIB
        Deflater comp = new Deflater(Deflater.BEST_COMPRESSION, true);

        DeflaterOutputStream dos = new DeflaterOutputStream(out, comp);

        dos.write(input);
        dos.finish();
        return out.toByteArray();
    }
}
