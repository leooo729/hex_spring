package com.example.spring_hex_practive.service;

public enum SwitchTrainKind {
    A("諾亞方舟號", "A"), B("霍格華茲號", "B");


    private String name;
    private String kind;

    SwitchTrainKind(String name, String kind) {
        this.name = name;
        this.kind = kind;
    }

    public static String getName(String kind) {
        for (SwitchTrainKind n : SwitchTrainKind.values()) {
            if (n.kind.equals(kind)) {
                return n.name;
            }
        }
        return null;
    }

    public static String getKind(String name) {
        for (SwitchTrainKind n : SwitchTrainKind.values()) {
            if (n.name.equals(name)) {
                return n.kind;
            }
        }
        return null;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getKind() {
        return kind;
    }
    public void setKind(String kind) {
        this.kind = kind;
    }
    @Override
    public String toString() {
        return super.toString();
    }
}
