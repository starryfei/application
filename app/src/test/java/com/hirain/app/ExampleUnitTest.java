//package com.hirain.app;
//
//import com.alibaba.fastjson.JSONObject;
//import com.hirain.app.entity.User;
//import com.hirain.app.zmq.ZmqEcuClient;
//
//
//
///**
// * Example local unit test, which will execute on the development machine (host).
// *
// * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
// */
//public class ExampleUnitTest {
//    @Test
//    public void addition_isCorrect() {
//
//
//    }
//
//    public static void main(String[] args) {
//        String image =    "iVBORw0KGgoAAAANSUhEUgAAAFoAAABaCAIAAAC3ytZVAAAAAXNSR0IArs4c6QAAAANzQklUCAgI\n" +
//                    "    2+FP4AAAIABJREFUeJzNfGlwXNd15jnn3vuWXrEDBLgAXMGdFCWZohRbjlyOKhPb48SZki3HnliO\n" +
//                    "    E8dJVaqmav5MZpKpSXkWO6lkapzKZKtKTZIaO0piK5YVxbG1WotlLSRFUlxAAiDWbjTQ2+u33XvP\n" +
//                    "    /GiQBLWSIODMV+9HN4BunPu9c88960NOHwVmAABmQIS1RqQI2RIYBAsWCXxg31opMGBmBAbiudLC\n" +
//                    "    UtDYsmM7gPLhMrJA64FWABIQQUJqEokSwAJaAAYAAASWAISs10ZQBACQ17hov1hrOIlAYEBGMMAM\n" +
//                    "    aIEjBMmcIlsAQEM5z3/66ZfOnCq//wM/JfO+KwuRJtcpsAG2kbCp0BESAiIAMjIAARODBCCEdPXC\n" +
//                    "    rVyyIABATr59jYt10A4wEtACWkDDrJEkEAESg4ccAxoAaUy2Uff+4q/+ae+eo3fd47IoLNTtCy+f\n" +
//                    "    rFUWOt1058b8of0jgLisHWgBCFgCCGAJFKxetrfQIa9ywczrQAYApoBkwNHgkJup1oPS/OLS0lIj\n" +
//                    "    8gBahCEid3ZtHNl6tJoUsn37Tp07eXbi8huTiwfuuHvvrg4/KZfGXpiaWNo43AnA0FYxsAAGmK5p\n" +
//                    "    9+pA9KYfyKsasYZcWGtJLgva4lioYstmz5xfnCktAmW7OneovJPt7XccLbCZmnotiB75wetlK+JC\n" +
//                    "    8ZUnZ1ta3vuvPpOCnKkuLE2WPnDg/VKEWjKDJko4bUlpbRqTQGAG9lcv6EoqEQFA3urS3w5EBEQ2\n" +
//                    "    SUgpUsUTZ2fmqirTvad361BqMtoIkE5gkVIQmEPuYC/dtHtn6AyGDnq9O3Zu2xWDT0odPzOWE+rx\n" +
//                    "    F84L3TTJzMBA54G9I9s3D4RJ2ZUIHLPR+OYbfDO4TrMQrtmON//u1oBojSHHOX/mzPico/IDieqv\n" +
//                    "    pR1+52bycsYYgaylg5bJkkAGa5hswol0hI0FIyil0jR2HGWT2BVgdJpBQ9yavPSKDqaOHRkZHsiS\n" +
//                    "    bYBJAcTayHydKYW1pIOZ0zR99dVXOzs787kPTMzXqyYvu7akKscSkDRArAUKdoXx0CiwwAhGxEw2\n" +
//                    "    AV8KsEnoKGJmQJkYgQKzCQCDh0s+lWcvvtjhNY/dtotsAmL1JwuvWDJKAWtIh1ahNADGBeMAuFY4\n" +
//                    "    2su9dPLEuYXR4ZEdFpSxkgUZ1hYtShapQ0TGGADAlScaWmACAMb2mzZIQdOAb9klBkcEun66NPX0\n" +
//                    "    /R880ANVlA4nFoUDliwCI1gAdbP+iCAAuJWddx1kUgTOAiQgmlbpunX+6u9eqrRGtgxvT4wFFIDI\n" +
//                    "    zIiIiGAZEVutFhHRm8w705veEAAtH7Ea0FoEk8p8Ydu2kQ8//o9TKXeFLYV+0ZoEMAHUAHbV61oz\n" +
//                    "    OsDmIJUgHOtlKiz/+OGnRe9dOnOMhStlRttl1cP28hjjOMZ3dXOQ2xe3L2CJDAAa0CrHDVo5a3cM\n" +
//                    "    D//s3z96SmaGmw2NjgSKCWJkIF6lQVk7OrjOwJZ6Zqrdv/9nz+vCoaa7uSZFkEDMBKQsQls7CBgs\n" +
//                    "    B0Hg+74xhq/fpIxw1f8hsASWQBNosB6wJDCAYaQjN+/E5CWi2Dl496PfP6NyG1IGiwbQXNthN49b\n" +
//                    "    pgMREIEIxBw4oh73/ulfnorEUdlxpA5ejSAxKrWOBmEBSICOI0VisVTu6upiZiHEW3QEr0hmjUnj\n" +
//                    "    sCURiC2yA6wYDGBqVSswDcxDA8NIbpxvOGfHF5mywAKQCLjt/q8Ca6cdngoMPfydFyqt3p6ho4Eu\n" +
//                    "    tLRtxlEQY5iwYWJEY4znedYYsLwSK7/GAlxlRCAtlOfGzp9TyICAlgkEADFQaJJqVLc5bGIm37/9\n" +
//                    "    n586HsVZ5ixYaY0BXOVxs2Z0pLpvej794cmzfm9+vDQR2ySJWUciiGxsuBlGcaoZgK1eXKj0dvXe\n" +
//                    "    gEgWwQ5v3jw02Pf6ide0bQqljWarHZ1mtS3WQlVpcCWKXzl1Trjdzz5zUmCRtSRChGR1q1gzOowe\n" +
//                    "    /Oajz27YtHFwa6fMhUixSTBpqCjWi0uNicmpIAiiKDLGVCuLSsh30o7rhCNCthnXGx3deerMC6lu\n" +
//                    "    MrNOVdxSQVOGkV+rO+DKepRMz1aee+618nwTVQaYAf9F6GgbDkRArJ083+9efPDT+y9cXHRzRxZ0\n" +
//                    "    oeHourA2rky8cbJTFcNmfiHonqw5sQRL5YbhSEMr8WLjYerm07BDWQ0QSmkxVbZpkSqQm6PioukA\n" +
//                    "    9ndv3DV2/KRMw2arOZ/YsoWFMEqT+nhVbdl2aOemjTs3FZ584htANVbCcHat6Vix1Hf8m6uZAeZQ\n" +
//                    "    1B586AvP/uBcoWOTMYAcCdY2Nq9fLBU2jM7FctGqxVYStJKM2/H8My8nQRolWE/SGKERhzHD2dOn\n" +
//                    "    +hyKQhOEUEtkPYJIQ73JieVyM4zAd/L9k3PVZsSRpWqLQXm1SBsUVrivnLn4Mz/3ybmFxbm5EjJI\n" +
//                    "    +pc6aK8wMnx4oBbL8xcZuStOItfRNmyFlaqT3xSIQl3lSmFkiR2wthFfOn4pbpj5paSWWFbQsEmp\n" +
//                    "    EU1duBScP8kpVVtUjrxyKOqBabWalUajakSlCdnihompchCbcqWmfEysdQu5INYxZWPVfepS+ZOf\n" +
//                    "    fmhqYtKkcdvZXVM6bkA7eEUmrYHVc5NVx90GmB0c9LLCusamS5WuQtEYTnSqRKqS0hvPPnb55ee3\n" +
//                    "    FjujQC+1OLCi2mjWwmAh5kKh6/JLTzlaN5t2ruVUQpnG8WBvPghb5+Yqly5O1evNvp7exdKsMPH8\n" +
//                    "    5bmMS4p0Pp+rx1jctOdHb8wUu4aOHL5NSPFmT3cN6LB2mRRYTpO81eZdcxmYEZw3zs5ns71C6cmL\n" +
//                    "    l9IgNs2wr6BUvNiYv5jjZg9XZl98pKt+Pt+a3dqTa4VxQwtS8vRrryZRvBBStRHt7M1OvPY8a7MU\n" +
//                    "    cCPQSWNJBEsbujvJyY+dO3vm5Im8onBx3uPEQx0ulU3UyrrAKCnTf36yqtlB5YFepWq8Kx039Gm6\n" +
//                    "    ehF0lecDouTVV74z2J9ztRcuNLpzussLh3ucTqhdePKbDx7b2RVO7t6cEypOwcagGnVTn51STE2b\n" +
//                    "    SYS3d9emxTPPN8szjGSNCeYmTGkiWJgdGBjsGRy0jK+dfL27u9sY4zv+xbHJYqHHNKqOUuAWG7Fo\n" +
//                    "    BRqMAbC82izImh20SbOoQz79+jMbNyHohkh9h72uvHn1tWd8ZSZPvXbPruH9vR42Z3aNDnPBTTCx\n" +
//                    "    wqku1mxlyTPYMm6AKtPh3Lm1c+rsqzZJpDWdFB9/6jtReVoxFoa2jVca2d6Nl+aWLpcaIWcyvSPn\n" +
//                    "    p2oyqpZnpxkEKr9Rb4IxwGRWmwS5NTqsvXqlLb80XTpyePvWkbxOgqBu8pni0898Z8NQ/+JSKWzU\n" +
//                    "    773zDtd30cSD2zbLnu4gDTVwc6kmkzRuhEBONUxEZ+H23ZtsVC3NXAYTdmfU/cfuOPni82+8fsrt\n" +
//                    "    GeR813i5mu/dEotckz3Kdk2UI4/s7OXJ6alZR2VarQjsLaUpCAgZwQIz4cprOa16NcPeDszfalav\n" +
//                    "    xCwEY/25c3ftcvOhpFgG6dh0+STGhbDzWP388Y/vTAf7U9ORHTywP+vhcH9nvlpKCJe0u68j6rv8\n" +
//                    "    7e54UuQ7lhKvb2THRweTzvEnpxI17hdH+2v394XhuVJ5aRr8nioPzQf5noGNwsYLcxWlOsdM5s4N\n" +
//                    "    vGfpB7I+OSsKLa8Iwqi09U727r3ouG5p13CzvOZd8cADnyovJanqMm7XZLnZDDWj06wsxc3onrve\n" +
//                    "    j+QzZD728QdAZgZ6h9KgQinEQc3L+D093ZAGCpJWq4WuuvvYoZypRjMXyYh64t71E/eoZKY0Ptbb\n" +
//                    "    UUjicGFxKbWUItaCFijRbKXGxocPbhd6UaQ1RQxAINTNyv82dNwKHGW2jmyfLkcLcf7CQlKzjvA7\n" +
//                    "    AbyksTg80NdZ7NQWrVUiU4SUO7N5V9ezJiDddHzvrqN3NmbGejK4WK\"\n";
//
//        User user = new User();
//        user.setName("aa");
//        user.setSex("1");
//        user.setImage(image);
//
//        String s = JSONObject.toJSONString(user);
//        System.out.println(s);
//
//    }
//}