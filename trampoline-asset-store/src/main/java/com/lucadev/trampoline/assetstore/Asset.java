package com.lucadev.trampoline.assetstore;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Wraps the asset input stream together with the meta data.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 9-6-18
 */
@AllArgsConstructor
@Getter
public class Asset {

    private final byte[] data;
    private final AssetMetaData metaData;
//
//    /**
//     * Read the asset input stream
//     *
//     * @return a byte array with the length that is set in the metadata size prop.
//     * @throws IOException when we cannot read the data.
//     */
//    public byte[] readData() throws IOException {
//        long size = metaData.getFileSize();
//        byte[] assetData = new byte[(int) size];
//        DataInputStream dis = new DataInputStream(this.inputStream);
//        dis.readFully(assetData);
//        return assetData;
//    }

    @Override
    public String toString() {
        return "Asset{" +
                "metaData=" + metaData +
                '}';
    }
}
