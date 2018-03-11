package net.batchik.jd.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public class FileChecksum {

    public static Optional<byte[]> checksum(final File file) throws NoSuchAlgorithmException, FileNotFoundException {
        final MessageDigest md = MessageDigest.getInstance("MD5");
        final byte[] buffer = new byte[4096];

        try (final FileInputStream input = new FileInputStream(file)) {
            int numRead;

            do {
                numRead = input.read(buffer);
                if (numRead > 0) {
                    md.update(buffer, 0, numRead);
                }
            } while (numRead == buffer.length);

            return Optional.of(md.digest());

        } catch (final IOException ex) {

        }

        return Optional.empty();
    }
}
