const CONFIG_MAP = new Map();

export default {
    get(key) {
        return CONFIG_MAP.get(key);
    },
    set(key, value) {
        CONFIG_MAP.set(key, value);
    }
}