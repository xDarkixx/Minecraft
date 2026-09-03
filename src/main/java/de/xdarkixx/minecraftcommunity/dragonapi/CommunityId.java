package de.xdarkixx.minecraftcommunity.dragonapi;

import java.util.Objects;

/** Small, loader-independent identifier helper for the community port. */
public record CommunityId(String namespace, String path) {
    public CommunityId {
        Objects.requireNonNull(namespace, "namespace");
        Objects.requireNonNull(path, "path");
        if (namespace.isBlank() || path.isBlank()) {
            throw new IllegalArgumentException("namespace and path must not be blank");
        }
    }

    public String asString() {
        return namespace + ":" + path;
    }

    @Override
    public String toString() {
        return asString();
    }
}
