package com.openclassrooms.go4lunch;

import static org.junit.Assert.assertEquals;

import com.openclassrooms.go4lunch.models.maps.Geometry;
import com.openclassrooms.go4lunch.models.maps.Location;
import com.openclassrooms.go4lunch.models.maps.OpeningHours;
import com.openclassrooms.go4lunch.models.maps.Photo;
import com.openclassrooms.go4lunch.models.maps.Result;
import com.openclassrooms.go4lunch.ui.main.list.ListPlaceAdapter;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class ListPlaceAdapterTest {
    ListPlaceAdapter listPlaceAdapter;
    Result place;

    @Before
    public void setup() {
        listPlaceAdapter = new ListPlaceAdapter();
        createPlaces();
    }

    @Test
    public void getOpeningTextTest() {
        int value = listPlaceAdapter.getOpeningText(place.getOpening_hours(),null);
        assertEquals(R.string.open_now, value);

        OpeningHours openinghourFalse = new OpeningHours();
        openinghourFalse.setOpen_now(false);
        place.setOpening_hours(openinghourFalse);
        value = listPlaceAdapter.getOpeningText(place.getOpening_hours(),null);
        assertEquals(R.string.closed, value);

        place.setOpening_hours(null);
        value = listPlaceAdapter.getOpeningText(place.getOpening_hours(),null);
        assertEquals(R.string.no_data, value);
    }

    @Test
    public void getPhotoUrlTest() {
        String reference = place.getPhotos().get(0).getPhoto_reference();
        String apiKey = BuildConfig.API_KEY;
        String check = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photo_reference="+reference+"&key="+apiKey;

        String value = listPlaceAdapter.getPhotoUrl(place.getPhotos());
        assertEquals(check,value);

        place.setPhotos(null);
        value = listPlaceAdapter.getPhotoUrl(place.getPhotos());
        assertEquals("",value);
    }

    void createPlaces() {
        Geometry geometry = new Geometry();
        Location location = new Location();
        location.setLat(48.8645475);
        location.setLng(2.3453768);
        geometry.setLocation(location);
        OpeningHours openingHours = new OpeningHours();
        openingHours.setOpen_now(true);
        List<Photo> photoList = new ArrayList<>();
        Photo photo = new Photo();
        photo.setPhoto_reference("Aap_uECj1_QWv9tfk9fuQ2wAWUjmB_BSiubeDgx3TBcrsjGedbE-3d58tfSAIeWaOpz4vPEhNbMulbBxMCW8gXvo43NzBIfsCeS_PunTdLogXbznPVjBC36oR2gYqU-eVbdE7cmqX6_PTtolMmNUpoRcUer4G1uuhUYXMEPCL_QKKf8IgBjI");
        photoList.add(photo);



        place = new Result();
        place.setPlace_id("ChIJHQXzdhhu5kcR44cVFMAi95c");
        place.setName("Le Comptoir de La Gastronomie");
        place.setGeometry(geometry);
        place.setVicinity("34 Rue Montmartre, Paris");
        place.setPhotos(photoList);
        place.setOpening_hours(openingHours);

    }
}
/*{
   "html_attributions" : [],
   "result" : {
      "address_components" : [
         {
            "long_name" : "34",
            "short_name" : "34",
            "types" : [ "street_number" ]
         },
         {
            "long_name" : "Rue Montmartre",
            "short_name" : "Rue Montmartre",
            "types" : [ "route" ]
         },
         {
            "long_name" : "Paris",
            "short_name" : "Paris",
            "types" : [ "locality", "political" ]
         },
         {
            "long_name" : "Département de Paris",
            "short_name" : "Département de Paris",
            "types" : [ "administrative_area_level_2", "political" ]
         },
         {
            "long_name" : "Île-de-France",
            "short_name" : "IDF",
            "types" : [ "administrative_area_level_1", "political" ]
         },
         {
            "long_name" : "France",
            "short_name" : "FR",
            "types" : [ "country", "political" ]
         },
         {
            "long_name" : "75001",
            "short_name" : "75001",
            "types" : [ "postal_code" ]
         }
      ],
      "adr_address" : "\u003cspan class=\"street-address\"\u003e34 Rue Montmartre\u003c/span\u003e, \u003cspan class=\"postal-code\"\u003e75001\u003c/span\u003e \u003cspan class=\"locality\"\u003eParis\u003c/span\u003e, \u003cspan class=\"country-name\"\u003eFrance\u003c/span\u003e",
      "business_status" : "OPERATIONAL",
      "formatted_address" : "34 Rue Montmartre, 75001 Paris, France",
      "formatted_phone_number" : "01 42 33 31 32",
      "geometry" : {
         "location" : {
            "lat" : 48.8645475,
            "lng" : 2.3453768
         },
         "viewport" : {
            "northeast" : {
               "lat" : 48.8658846302915,
               "lng" : 2.346655830291501
            },
            "southwest" : {
               "lat" : 48.8631866697085,
               "lng" : 2.343957869708498
            }
         }
      },
      "icon" : "https://maps.gstatic.com/mapfiles/place_api/icons/v1/png_71/restaurant-71.png",
      "icon_background_color" : "#FF9E67",
      "icon_mask_base_uri" : "https://maps.gstatic.com/mapfiles/place_api/icons/v2/restaurant_pinlet",
      "international_phone_number" : "+33 1 42 33 31 32",
      "name" : "Le Comptoir de La Gastronomie",
      "opening_hours" : {
         "open_now" : false,
         "periods" : [
            {
               "close" : {
                  "day" : 2,
                  "time" : "1430"
               },
               "open" : {
                  "day" : 2,
                  "time" : "1200"
               }
            },
            {
               "close" : {
                  "day" : 2,
                  "time" : "2230"
               },
               "open" : {
                  "day" : 2,
                  "time" : "1900"
               }
            },
            {
               "close" : {
                  "day" : 3,
                  "time" : "1430"
               },
               "open" : {
                  "day" : 3,
                  "time" : "1200"
               }
            },
            {
               "close" : {
                  "day" : 3,
                  "time" : "2230"
               },
               "open" : {
                  "day" : 3,
                  "time" : "1900"
               }
            },
            {
               "close" : {
                  "day" : 4,
                  "time" : "1430"
               },
               "open" : {
                  "day" : 4,
                  "time" : "1200"
               }
            },
            {
               "close" : {
                  "day" : 4,
                  "time" : "2230"
               },
               "open" : {
                  "day" : 4,
                  "time" : "1900"
               }
            },
            {
               "close" : {
                  "day" : 5,
                  "time" : "1430"
               },
               "open" : {
                  "day" : 5,
                  "time" : "1200"
               }
            },
            {
               "close" : {
                  "day" : 5,
                  "time" : "2230"
               },
               "open" : {
                  "day" : 5,
                  "time" : "1900"
               }
            },
            {
               "close" : {
                  "day" : 6,
                  "time" : "1430"
               },
               "open" : {
                  "day" : 6,
                  "time" : "1200"
               }
            },
            {
               "close" : {
                  "day" : 6,
                  "time" : "2230"
               },
               "open" : {
                  "day" : 6,
                  "time" : "1900"
               }
            }
         ],
         "weekday_text" : [
            "lundi: Fermé",
            "mardi: 12:00 – 14:30, 19:00 – 22:30",
            "mercredi: 12:00 – 14:30, 19:00 – 22:30",
            "jeudi: 12:00 – 14:30, 19:00 – 22:30",
            "vendredi: 12:00 – 14:30, 19:00 – 22:30",
            "samedi: 12:00 – 14:30, 19:00 – 22:30",
            "dimanche: Fermé"
         ]
      },
      "photos" : [
         {
            "height" : 1147,
            "html_attributions" : [
               "\u003ca href=\"https://maps.google.com/maps/contrib/101225858123518379794\"\u003eLe Comptoir de La Gastronomie\u003c/a\u003e"
            ],
            "photo_reference" : "Aap_uECj1_QWv9tfk9fuQ2wAWUjmB_BSiubeDgx3TBcrsjGedbE-3d58tfSAIeWaOpz4vPEhNbMulbBxMCW8gXvo43NzBIfsCeS_PunTdLogXbznPVjBC36oR2gYqU-eVbdE7cmqX6_PTtolMmNUpoRcUer4G1uuhUYXMEPCL_QKKf8IgBjI",
            "width" : 2000
         },
         {
            "height" : 5237,
            "html_attributions" : [
               "\u003ca href=\"https://maps.google.com/maps/contrib/101225858123518379794\"\u003eLe Comptoir de La Gastronomie\u003c/a\u003e"
            ],
            "photo_reference" : "Aap_uECBoQ2OUyIJLJFmCYPoY0UVGgop2z_kGY-AVY2HN6gSrmQsqJv7gvIWPoMJvYNVSctjtp1vpncHkNlap6ay3L67HtO3U-Sw-2-TW1RuX3doyLP5zjmebBpY3iG5fByyrg5AIb3FNcoFx-4LfUo8A7NA1tx7oTf0J6Sj8kSEpk7tRBWf",
            "width" : 5237
         },
         {
            "height" : 892,
            "html_attributions" : [
               "\u003ca href=\"https://maps.google.com/maps/contrib/109178936832129224577\"\u003eIdo Simyoni\u003c/a\u003e"
            ],
            "photo_reference" : "Aap_uEDbw2-M5mHAyeNT2fjIBTj21iWQwJEAlcfY2eHRKnxoqN8ft6U7rCcuC05_u1vGczPeIrjRMnpTF72-5uUIU40Ku8-Odj6fOTzcUW2BnCipZUxYKE_tMERyUlvGVkaqqVpibtjHm8PhAPddB8FbrSB16OLJRCn-DdYvIwI_AMkrrH_O",
            "width" : 1284
         },
         {
            "height" : 2160,
            "html_attributions" : [
               "\u003ca href=\"https://maps.google.com/maps/contrib/101845015790443140317\"\u003eM 171D\u003c/a\u003e"
            ],
            "photo_reference" : "Aap_uEDnSI0ExfOflXSBup7X7Xj4i31Qx2l7zhkIQc8pwBiJQut8wffWP4FrFb2eaJcMqdn3L-Z8_qC_NnG9O7OU6FGp1Mo-cV9m7XCdXd5m27DyxMK2-peXt_TSqBWXD5pieKkZPeqzOFEzT9Pxv2kDhiA9PY8GmkUJS5naMa449QQphVCr",
            "width" : 2880
         },
         {
            "height" : 2492,
            "html_attributions" : [
               "\u003ca href=\"https://maps.google.com/maps/contrib/105471884770937254513\"\u003eBill Raddatz\u003c/a\u003e"
            ],
            "photo_reference" : "Aap_uEAXyNqbpq-RoT-FG6ZnfCvUDRJ7Hvh_3yl26cQuF4XBX9z5Z0MBlY_jB42d4GdzWdU1WoGJL8yjuEBzZDjjNdkN0IHMCYcnFOEb6Va_g1VVuTG9egYrHL_8WmpGtwo11iUZk4k2QUr--Usk8ES0rn_cpYfN4ypUcQpG5nAkp63CSfCH",
            "width" : 4032
         },
         {
            "height" : 2160,
            "html_attributions" : [
               "\u003ca href=\"https://maps.google.com/maps/contrib/101845015790443140317\"\u003eM 171D\u003c/a\u003e"
            ],
            "photo_reference" : "Aap_uECRgm4XWEhnqDq-Q7wJqCTQ7pJqBMd7Omua-Lix2yE4UvYPxtBELC0wAgVAWW-R1M9dGbp_Fwj0W19hqxGcvgN4gSuRMb_ue6ztVRepSEY6jOPLUR81KzfHnZTAPR4GV5au6k-KY96N1f53WRLDYwpE4B9KZLSXwhIJVXfevxMLIBqS",
            "width" : 2880
         },
         {
            "height" : 2736,
            "html_attributions" : [
               "\u003ca href=\"https://maps.google.com/maps/contrib/114501872979152780145\"\u003e余庆\u003c/a\u003e"
            ],
            "photo_reference" : "Aap_uEDuj3liK7VKgq0cMgJ4OZ713V9QKEdVoO_2R_lnjA_0_39967YX0tOHxTU6rdWqeMJgnGlD7-iC-BdGCIB-SAcPS7RcQQcNwcDxYYu1UCAx-dDQnEoR-4ZtAipDXUOLEAcX_0qsRJSNLwa0a6FEjHzJDYuMN45MTCHdIgscK4EuJCZq",
            "width" : 3648
         },
         {
            "height" : 3024,
            "html_attributions" : [
               "\u003ca href=\"https://maps.google.com/maps/contrib/109378042435927617695\"\u003eFuHo Chu\u003c/a\u003e"
            ],
            "photo_reference" : "Aap_uEBnYhEh1cVR3jreJrlrJfVsEHZwmcIHcjoqLJsWfQpeys0JcbshORBpw-EZC0_5xFKb3vKoyRcm1uQExXbFWGRt3bMQbXBnY0fS9KxWUOZyvJcYz0eGVSmfpO7t6WFBnoOHNKqZ0Ein_Lrv4tG7r_q7ufMLkgKEpfCEU8-5h-P7zA8J",
            "width" : 3024
         },
         {
            "height" : 3466,
            "html_attributions" : [
               "\u003ca href=\"https://maps.google.com/maps/contrib/101225858123518379794\"\u003eLe Comptoir de La Gastronomie\u003c/a\u003e"
            ],
            "photo_reference" : "Aap_uEBTyBpwRzSFOg_WUdGYy-WiTLgeQjaxq3SllC9ya1IqQyHnKj2J5e3g5Gnp6bqoVZWT8A-7MaaYr3P36wcM6jCcUsnrikKDNQ58qvO6874e6-yAbqbLiI1u1BQ2up9UsLV8mR9HEkOpBn85__YutwjS0MUWmOyn3MQkc28DgFysx-2R",
            "width" : 5000
         },
         {
            "height" : 3024,
            "html_attributions" : [
               "\u003ca href=\"https://maps.google.com/maps/contrib/101620119197966860836\"\u003eLILLY N\u003c/a\u003e"
            ],
            "photo_reference" : "Aap_uEDHcGEi6dnyAlxTJypvHsy9JJaPdHryAY_OHDViwxslfAkwzZ7XUqscZos9X0Lcr4gEoymxYsekZRNbNv7uA-zWwSwPIIWfaF57cP-QafaIduGZJXnrmQPpC0p6BsQFaxVCwwD_zJa5hpwey2KbEUKf6UTmQYYrOdjGwdPsVAdipucn",
            "width" : 3024
         }
      ],
      "place_id" : "ChIJHQXzdhhu5kcR44cVFMAi95c",
      "plus_code" : {
         "compound_code" : "V87W+R5 Paris, France",
         "global_code" : "8FW4V87W+R5"
      },
      "price_level" : 2,
      "rating" : 4.4,
      "reference" : "ChIJHQXzdhhu5kcR44cVFMAi95c",
      "reviews" : [
         {
            "author_name" : "fred Haut de France",
            "author_url" : "https://www.google.com/maps/contrib/107833126693009156097/reviews",
            "language" : "fr",
            "profile_photo_url" : "https://lh3.googleusercontent.com/a-/AOh14GgGFjJLK_MZZe9ok4NUL9fQs19isHxHCyChCfJl=s128-c0x00000000-cc-rp-mo",
            "rating" : 3,
            "relative_time_description" : "il y a un mois",
            "text" : "Les plats sont bons . Même excellents.\nLe personnel agréable.\nJe mets 3 étoiles. Juste pour acheter un sourire au patron' et pour une erreur ++ ?? sur l'addition..",
            "time" : 1630854813
         },
         {
            "author_name" : "Laurent Jacquemin",
            "author_url" : "https://www.google.com/maps/contrib/100689604546461008852/reviews",
            "language" : "fr",
            "profile_photo_url" : "https://lh3.googleusercontent.com/a-/AOh14Gi5ZFfTftIaeGtZ3t6mEkE0dql3RiGjK0O0Nqm_Gg=s128-c0x00000000-cc-rp-mo-ba3",
            "rating" : 4,
            "relative_time_description" : "il y a un an",
            "text" : "Produits et service de qualité",
            "time" : 1600067566
         },
         {
            "author_name" : "Solomiya Hrytsenyak",
            "author_url" : "https://www.google.com/maps/contrib/115168264671572550697/reviews",
            "language" : "fr",
            "profile_photo_url" : "https://lh3.googleusercontent.com/a-/AOh14Gh6aBwT_l2o6_zN138mlYrnNOpSJIsMjkuNjGf0=s128-c0x00000000-cc-rp-mo",
            "rating" : 5,
            "relative_time_description" : "il y a un mois",
            "text" : "Le restaurant qui m'a été fortement recommandé par mes amis. Et ils avaient raison ! Les plats, le café étaient trop bons et le service est parfait. Je vais sûrement y revenir !",
            "time" : 1631747564
         },
         {
            "author_name" : "mystifie adri",
            "author_url" : "https://www.google.com/maps/contrib/101261016922584557113/reviews",
            "language" : "fr",
            "profile_photo_url" : "https://lh3.googleusercontent.com/a/AATXAJwso_SXkDRsWNJzxXZr7jrmgkHYxZs6NKU3dKxK=s128-c0x00000000-cc-rp-mo-ba4",
            "rating" : 5,
            "relative_time_description" : "il y a 3 mois",
            "text" : "Les plats sont savoureux, le service est super. Le petit plus se trouve au niveau de l'épicerie fine, qui propose des produits de différents terroirs délicieux. La serveuse actuelle est d'une gentillesse rare. Au plaisir d'y retourner.",
            "time" : 1626354772
         },
         {
            "author_name" : "Frédéric MOMBLE-AVICE",
            "author_url" : "https://www.google.com/maps/contrib/107165935645192696845/reviews",
            "language" : "fr",
            "profile_photo_url" : "https://lh3.googleusercontent.com/a-/AOh14GjE9Q6x1Dkb_-0JHdi2oDpjsp6R6-23PRou_U8csw=s128-c0x00000000-cc-rp-mo-ba6",
            "rating" : 2,
            "relative_time_description" : "il y a un mois",
            "text" : "Très déçu.  La bouchée aux ris de veau : c'est une farce . Pour 13 euros vous avez une bouchée avec un maigre morceau de ris de veau et du poulet à foison. A Bayonne,  j'achète pour 5,5 euros une bouchée à la reine aux ris de veau dont je ne cherche pas les ris de veau.",
            "time" : 1631390087
         }
      ],
      "types" : [ "restaurant", "food", "point_of_interest", "store", "establishment" ],
      "url" : "https://maps.google.com/?cid=10950259227340670947",
      "user_ratings_total" : 1782,
      "utc_offset" : 120,
      "vicinity" : "34 Rue Montmartre, Paris",
      "website" : "http://www.comptoirdelagastronomie.com/"
   },
   "status" : "OK"
}*/