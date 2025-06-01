package com.prateleira_inteligente.util;


import org.hashids.Hashids;
import org.springframework.stereotype.Component;

@Component
public class HashId {

    private final Hashids hashids;

    public HashId() {
        this.hashids = new Hashids("OblivionGOTY2025" + 32);
    }

    public String encode(Long id) {
        return hashids.encode(id);
    }

    public Long decode(String hash) {
        long[] result = hashids.decode(hash);
        return result.length > 0 ? result[0] : null;
    }
}
