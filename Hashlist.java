public class Hashlist<K, V> {

    private class Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next;

        public Entry(K key, V value){ 
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    private static final int DefaultCapacity = 10;
    private static final float Defaultloadfactor = 0.75f;

    private Entry<K, V>[] table;
    private int capacity;
    private int size;
    private final float loadfactor;

    @SuppressWarnings("unchecked") 
    public Hashlist(){
        this.capacity = DefaultCapacity;
        this.loadfactor = Defaultloadfactor;
        this.size = 0;
        this.table = new Entry[capacity];
    }

    @SuppressWarnings("unchecked") 
    public Hashlist(int initialCapacity){
        this.capacity = initialCapacity;
        this.loadfactor = Defaultloadfactor;
        this.size = 0;
        this.table = new Entry[capacity];
    }

    @SuppressWarnings("unchecked") 
    public Hashlist(int initialCapacity, float loadfactor){
        this.capacity = initialCapacity;
        this.loadfactor = loadfactor;
        this.size = 0;
        this.table = new Entry[capacity];
    }

    private int hash(K key){
        if (key == null){
            return 0;
        }
        return Math.abs(key.hashCode()) % capacity;
    }

    public void put(K key, V value){
        int index = hash(key);
        Entry<K, V> current = table[index];

        while(current != null){
            if(equalsKey(current.key, key)){
                current.value = value;
                return;
            }
            current = current.next;
        }

        Entry<K, V> newEntry = new Entry<>(key, value);
        newEntry.next = table[index];
        table[index] = newEntry;
        size++;

        if((float) size / capacity >= loadfactor){
            resize();
        }
    }

    public V get(K key){
        int index = hash(key);
        Entry<K, V> current = table[index];

        while(current != null){
            if(equalsKey(current.key, key)){
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    public V remove(K key){
        int index = hash(key);
        Entry<K, V> current = table[index];
        Entry<K, V> previous = null;

        while(current != null){
            if(equalsKey(current.key, key)){
                if (previous == null){
                    table[index] = current.next;
                } else {
                    previous.next = current.next;
                }

                size--;
                return current.value;
            }
            previous = current;
            current = current.next;
        }

        return null;
    }

    public boolean ContainsKey(K key){
        return get(key) != null;
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    @SuppressWarnings("unchecked") 
    public void clear(){
        table = new Entry[capacity];
        size = 0;
    }

    @SuppressWarnings("unchecked") 
    private void resize() {
        int oldCapacity = capacity;
        capacity = 2 * oldCapacity;
        Entry<K, V>[] oldTable = table;
        table = new Entry[capacity];
        size = 0;

        for(int i = 0; i < oldCapacity; i++){
            Entry<K, V> current = oldTable[i];
            while(current != null){
                put(current.key, current.value);
                current = current.next;
            }
        }
    }

    /**
     * @param Key1 erster Schlüssel
     * @param Key2 2 Schlüssel
     * @return Beide Schlüssel sind gleich sonst null 
     **/
    private boolean equalsKey(K k1, K k2){
        if(k1 == null && k2 == null){
            return true;
        }
        if(k1 == null || k2 == null){
            return false;
        }
        return k1.equals(k2);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        boolean firstEntry = true;
        for(int i = 0; i < capacity; i++){
            Entry<K, V> current = table[i];
            while(current != null){
                if(!firstEntry){
                    sb.append(", ");
                }
                sb.append(current.key).append("=").append(current.value);
                firstEntry = false;
                current = current.next;
            }
        }

        sb.append("}");
        return sb.toString();
    }

    public static void main(String[] args) {
        Hashlist<String, Integer> hashMap = new Hashlist<>();

        hashMap.put("eins", 1);
        hashMap.put("zwei", 2);
        hashMap.put("drei", 3);
        hashMap.put(null, 0);

        System.out.println("HashMap: " + hashMap);
        System.out.println();
        System.out.println("Wert in eins: " + hashMap.get("eins"));
        System.out.println("Wert in Zwei: " + hashMap.get("zwei"));
        System.out.println("Wert in Drei: " + hashMap.get(null));
        System.out.println("Wert in Vier: " + hashMap.get("vier"));
        System.out.println();

        System.out.println("Enthält drei? " + hashMap.ContainsKey("drei"));
        System.out.println("Enthält vier? " + hashMap.ContainsKey("vier"));
        System.out.println();

        System.out.println("Entferne 2: " + hashMap.remove("zwei"));
        System.out.println("Enthält 2? " + hashMap.ContainsKey("zwei"));
        System.out.println("HashMap remove() : " + hashMap);
        System.out.println();

        System.out.println("Hashmap.lenght: " + hashMap.size());
        System.out.println();

        System.out.println("Leer ?  " + hashMap.isEmpty());
        System.out.println();

        hashMap.clear();
        System.out.println("HashMap: " + hashMap);
        System.out.println("leer? " + hashMap.isEmpty());
    }
}
