package com.example.spring_hex_practive.domain.aggregate.util;


public enum SwitchTrainKind {
    A("諾亞方舟號", "A"), B("霍格華茲號", "B");

    private String name;
    private String kind;

    SwitchTrainKind(String name, String kind) {
        this.name = name;
        this.kind = kind;
    }

    public static String getName(String kind) {

        for (SwitchTrainKind train : SwitchTrainKind.values()) {
            if (train.kind.equals(kind)) {
                return train.name;
            }
        }
        return null;
    }

    public static String getKind(String name) {
        for (SwitchTrainKind train : SwitchTrainKind.values()) {
            if (train.name.equals(name)) {
                return train.kind;
            }
        }
        return null;
    }

}

//----------------------switch
//    public String switchKind(String trainKind) {
//        switch (trainKind) {
//            case "A":
//                return "諾亞方舟號";
//            case "諾亞方舟號":
//                return "A";
//            case "B":
//                return "霍格華茲號";
//            case "霍格華茲號":
//                return "B";
//            default:
//                return null;
//        }
//    }
