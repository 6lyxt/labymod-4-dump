// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util;

import net.labymod.api.client.component.Component;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.resources.texture.ThemeTextureLocation;
import java.util.Locale;
import net.labymod.api.client.gui.icon.Icon;

public enum CountryCode
{
    AD(new String[] { "Andorra" }), 
    AE(new String[] { "United Arab Emirates" }), 
    AF(new String[] { "Afghanistan" }), 
    AG(new String[] { "Antigua and Barbuda" }), 
    AI(new String[] { "Anguilla" }), 
    AL(new String[] { "Albania" }), 
    AM(new String[] { "Armenia" }), 
    AO(new String[] { "Angola" }), 
    AQ(new String[] { "Antarctica" }), 
    AR(new String[] { "Argentina" }), 
    AS(new String[] { "American Samoa" }), 
    AT(new String[] { "Austria" }), 
    AU(new String[] { "Australia" }), 
    AW(new String[] { "Aruba" }), 
    AX(new String[] { "\u00c5land Islands" }), 
    AZ(new String[] { "Azerbaijan" }), 
    BA(new String[] { "Bosnia and Herzegovina" }), 
    BB(new String[] { "Barbados" }), 
    BD(new String[] { "Bangladesh" }), 
    BE(new String[] { "Belgium" }), 
    BF(new String[] { "Burkina Faso" }), 
    BG(new String[] { "Bulgaria" }), 
    BH(new String[] { "Bahrain" }), 
    BI(new String[] { "Burundi" }), 
    BJ(new String[] { "Benin" }), 
    BL(new String[] { "Saint Barth\u00e9lemy" }), 
    BM(new String[] { "Bermuda" }), 
    BN(new String[] { "Brunei Darussalam" }), 
    BO(new String[] { "Bolivia", "Plurinational State of Bolivia" }), 
    BQ(new String[] { "Bonaire, Sint Eustatius and Saba" }), 
    BR(new String[] { "Brazil" }), 
    BS(new String[] { "Bahamas" }), 
    BT(new String[] { "Bhutan" }), 
    BV(new String[] { "Bouvet Island" }), 
    BW(new String[] { "Botswana" }), 
    BY(new String[] { "Belarus" }), 
    BZ(new String[] { "Belize" }), 
    CA(new String[] { "Canada" }), 
    CC(new String[] { "Cocos Islands", "Cocos (Keeling) Islands" }), 
    CD(new String[] { "Democratic Republic of Congo", "the Democratic Republic of the Congo" }), 
    CF(new String[] { "Central African Republic" }), 
    CG(new String[] { "Congo" }), 
    CH(new String[] { "Switzerland" }), 
    CI(new String[] { "C\u00f4te d'Ivoire" }), 
    CK(new String[] { "Cook Islands" }), 
    CL(new String[] { "Chile" }), 
    CM(new String[] { "Cameroon" }), 
    CN(new String[] { "China" }), 
    CO(new String[] { "Colombia" }), 
    CR(new String[] { "Costa Rica" }), 
    CU(new String[] { "Cuba" }), 
    CV(new String[] { "Cabo Verde", "Cape Verde" }), 
    CW(new String[] { "Cura\u00e7ao" }), 
    CX(new String[] { "Christmas Island" }), 
    CY(new String[] { "Cyprus" }), 
    CZ(new String[] { "Czechia", "Czech Republic" }), 
    DE(new String[] { "Germany" }), 
    DJ(new String[] { "Djibouti" }), 
    DK(new String[] { "Denmark" }), 
    DM(new String[] { "Dominica" }), 
    DO(new String[] { "Dominican Republic" }), 
    DZ(new String[] { "Algeria" }), 
    EC(new String[] { "Ecuador" }), 
    EE(new String[] { "Estonia" }), 
    EG(new String[] { "Egypt" }), 
    EH(new String[] { "Western Sahara" }), 
    ER(new String[] { "Eritrea" }), 
    ES(new String[] { "Spain" }), 
    ET(new String[] { "Ethiopia" }), 
    EU(new String[] { "European Union" }), 
    FI(new String[] { "Finland" }), 
    FJ(new String[] { "Fiji" }), 
    FK(new String[] { "Falkland Islands", "Malvinas" }), 
    FM(new String[] { "Micronesia", "Federated States of Micronesia" }), 
    FO(new String[] { "Faroe Islands" }), 
    FR(new String[] { "France" }), 
    GA(new String[] { "Gabon" }), 
    GD_NG(new String[] { "England" }), 
    GD_IR(new String[] { "Ireland" }), 
    GD_CT(new String[] { "Scotland" }), 
    GD_LS(new String[] { "Wales" }), 
    GB(new String[] { "United Kingdom", "United Kingdom of Great Britain and Northern Ireland", "Great Britain" }), 
    GD(new String[] { "Grenada" }), 
    GE(new String[] { "Georgia" }), 
    GF(new String[] { "French Guiana" }), 
    GG(new String[] { "Guernsey" }), 
    GH(new String[] { "Ghana" }), 
    GI(new String[] { "Gibraltar" }), 
    GL(new String[] { "Greenland" }), 
    GM(new String[] { "Gambia" }), 
    GN(new String[] { "Guinea" }), 
    GP(new String[] { "Guadeloupe" }), 
    GQ(new String[] { "Equatorial Guinea" }), 
    GR(new String[] { "Greece" }), 
    GS(new String[] { "South Georgia", "South Georgia and the South Sandwich Islands" }), 
    GT(new String[] { "Guatemala" }), 
    GU(new String[] { "Guam" }), 
    GW(new String[] { "Guinea-Bissau" }), 
    GY(new String[] { "Guyana" }), 
    HK(new String[] { "Hong Kong" }), 
    HM(new String[] { "Heard Island and McDonald Islands" }), 
    HN(new String[] { "Honduras" }), 
    HR(new String[] { "Croatia" }), 
    HT(new String[] { "Haiti" }), 
    HU(new String[] { "Hungary" }), 
    ID(new String[] { "Indonesia" }), 
    IE(new String[] { "Ireland" }), 
    IL(new String[] { "Israel" }), 
    IM(new String[] { "Isle of Man" }), 
    IN(new String[] { "India" }), 
    IO(new String[] { "British Indian Ocean Territory" }), 
    IQ(new String[] { "Iraq" }), 
    IR(new String[] { "Iran", "Islamic Republic of Iran" }), 
    IS(new String[] { "Iceland" }), 
    IT(new String[] { "Italy" }), 
    JE(new String[] { "Jersey" }), 
    JM(new String[] { "Jamaica" }), 
    JO(new String[] { "Jordan" }), 
    JP(new String[] { "Japan" }), 
    KE(new String[] { "Kenya" }), 
    KG(new String[] { "Kyrgyzstan" }), 
    KH(new String[] { "Cambodia" }), 
    KI(new String[] { "Kiribati" }), 
    KM(new String[] { "Comoros" }), 
    KN(new String[] { "Saint Kitts and Nevis" }), 
    KP(new String[] { "North Korea", "the Democratic People's Republic of Korea" }), 
    KR(new String[] { "South Korea", "the Republic of Korea" }), 
    KW(new String[] { "Kuwait" }), 
    KY(new String[] { "Cayman Islands" }), 
    KZ(new String[] { "Kazakhstan" }), 
    LA(new String[] { "Lao People's Democratic Republic" }), 
    LB(new String[] { "Lebanon" }), 
    LC(new String[] { "Saint Lucia" }), 
    LI(new String[] { "Liechtenstein" }), 
    LK(new String[] { "Sri Lanka" }), 
    LR(new String[] { "Liberia" }), 
    LS(new String[] { "Lesotho" }), 
    LT(new String[] { "Lithuania" }), 
    LU(new String[] { "Luxembourg" }), 
    LV(new String[] { "Latvia" }), 
    LY(new String[] { "Libya" }), 
    MA(new String[] { "Morocco" }), 
    MC(new String[] { "Monaco" }), 
    MD(new String[] { "Moldova", "the Republic of Moldova" }), 
    ME(new String[] { "Montenegro" }), 
    MF(new String[] { "Saint Martin (French part)", "Saint Martin" }), 
    MG(new String[] { "Madagascar" }), 
    MH(new String[] { "Marshall Islands" }), 
    MK(new String[] { "Republic of North Macedonia" }), 
    ML(new String[] { "Mali" }), 
    MM(new String[] { "Myanmar" }), 
    MN(new String[] { "Mongolia" }), 
    MO(new String[] { "Macao" }), 
    MP(new String[] { "Northern Mariana Islands" }), 
    MQ(new String[] { "Martinique" }), 
    MR(new String[] { "Mauritania" }), 
    MS(new String[] { "Montserrat" }), 
    MT(new String[] { "Malta" }), 
    MU(new String[] { "Mauritius" }), 
    MV(new String[] { "Maldives" }), 
    MW(new String[] { "Malawi" }), 
    MX(new String[] { "Mexico" }), 
    MY(new String[] { "Malaysia" }), 
    MZ(new String[] { "Mozambique" }), 
    NA(new String[] { "Namibia" }), 
    NC(new String[] { "New Caledonia" }), 
    NE(new String[] { "Niger" }), 
    NF(new String[] { "Norfolk Island" }), 
    NG(new String[] { "Nigeria" }), 
    NI(new String[] { "Nicaragua" }), 
    NL(new String[] { "Netherlands" }), 
    NO(new String[] { "Norway" }), 
    NP(new String[] { "Nepal" }), 
    NR(new String[] { "Nauru" }), 
    NU(new String[] { "Niue" }), 
    NZ(new String[] { "New Zealand" }), 
    OM(new String[] { "Oman" }), 
    PA(new String[] { "Panama" }), 
    PE(new String[] { "Peru" }), 
    PF(new String[] { "French Polynesia" }), 
    PG(new String[] { "Papua New Guinea" }), 
    PH(new String[] { "Philippines" }), 
    PK(new String[] { "Pakistan" }), 
    PL(new String[] { "Poland" }), 
    PM(new String[] { "Saint Pierre and Miquelon" }), 
    PN(new String[] { "Pitcairn" }), 
    PR(new String[] { "Puerto Rico" }), 
    PS(new String[] { "Palestine", "State of Palestine" }), 
    PT(new String[] { "Portugal" }), 
    PW(new String[] { "Palau" }), 
    PY(new String[] { "Paraguay" }), 
    QA(new String[] { "Qatar" }), 
    RE(new String[] { "R\u00e9union" }), 
    RO(new String[] { "Romania" }), 
    RS(new String[] { "Serbia" }), 
    RU(new String[] { "Russia", "Russian Federation" }), 
    RW(new String[] { "Rwanda" }), 
    SA(new String[] { "Saudi Arabia" }), 
    SB(new String[] { "Solomon Islands" }), 
    SC(new String[] { "Seychelles" }), 
    SD(new String[] { "Sudan" }), 
    SE(new String[] { "Sweden" }), 
    SG(new String[] { "Singapore" }), 
    SH(new String[] { "Saint Helena", "Saint Helena, Ascension and Tristan da Cunha" }), 
    SI(new String[] { "Slovenia" }), 
    SJ(new String[] { "Svalbard and Jan Mayen" }), 
    SK(new String[] { "Slovakia" }), 
    SL(new String[] { "Sierra Leone" }), 
    SM(new String[] { "San Marino" }), 
    SN(new String[] { "Senegal" }), 
    SO(new String[] { "Somalia" }), 
    SR(new String[] { "Suriname" }), 
    SS(new String[] { "South Sudan" }), 
    ST(new String[] { "S\u00e3o Tom\u00e9 and Pr\u00edncipe", "Sao Tome and Principe" }), 
    SV(new String[] { "El Salvador" }), 
    SX(new String[] { "Sint Maarten (Dutch part)", "Sint Maarten" }), 
    SY(new String[] { "Syrian Arab Republic" }), 
    SZ(new String[] { "Eswatini" }), 
    TC(new String[] { "Turks and Caicos Islands" }), 
    TD(new String[] { "Chad" }), 
    TF(new String[] { "French Southern Territories" }), 
    TG(new String[] { "Togo" }), 
    TH(new String[] { "Thailand" }), 
    TJ(new String[] { "Tajikistan" }), 
    TK(new String[] { "Tokelau" }), 
    TL(new String[] { "Timor-Leste" }), 
    TM(new String[] { "Turkmenistan" }), 
    TN(new String[] { "Tunisia" }), 
    TO(new String[] { "Tonga" }), 
    TR(new String[] { "Turkey", "T\u00fcrkiye" }), 
    TT(new String[] { "Trinidad and Tobago" }), 
    TV(new String[] { "Tuvalu" }), 
    TW(new String[] { "Taiwan", "Taiwan (Province of China)" }), 
    TZ(new String[] { "Tanzania", "United Republic of Tanzania" }), 
    UA(new String[] { "Ukraine" }), 
    UG(new String[] { "Uganda" }), 
    UM(new String[] { "United States Minor Outlying Islands" }), 
    US(new String[] { "United States of America" }), 
    UY(new String[] { "Uruguay" }), 
    UZ(new String[] { "Uzbekistan" }), 
    VA(new String[] { "Holy See" }), 
    VC(new String[] { "Saint Vincent and the Grenadines" }), 
    VE(new String[] { "Venezuela", "Bolivarian Republic of Venezuela" }), 
    VG(new String[] { "Virgin Islands (British)" }), 
    VI(new String[] { "Virgin Islands (U.S.)" }), 
    VN(new String[] { "Viet Nam" }), 
    VU(new String[] { "Vanuatu" }), 
    WF(new String[] { "Wallis and Futuna" }), 
    WS(new String[] { "Samoa" }), 
    XK(new String[] { "Kosovo" }), 
    YE(new String[] { "Yemen" }), 
    YT(new String[] { "Mayotte" }), 
    ZA(new String[] { "South Africa" }), 
    ZM(new String[] { "Zambia" }), 
    ZW(new String[] { "Zimbabwe" });
    
    private static final CountryCode[] VALUES;
    public static final int FLAG_WIDTH = 49;
    public static final int FLAG_HEIGHT = 32;
    private final Icon icon;
    private final String[] names;
    private final String translationKey;
    
    private CountryCode(final String[] names) {
        this.names = names;
        this.translationKey = "labymod.country." + this.name().toLowerCase(Locale.ROOT);
        final int ordinal = this.ordinal();
        final int x = ordinal % 16;
        final int y = ordinal / 16;
        final ThemeTextureLocation texture = ThemeTextureLocation.of("flags", 784, 512);
        this.icon = Icon.sprite(texture, x, y, 49, 32);
    }
    
    @Nullable
    public static CountryCode fromCode(final String string) {
        final String search = StringUtil.toUppercase(string).replace("-", "_");
        for (final CountryCode countryCode : CountryCode.VALUES) {
            if (countryCode.name().equals(search)) {
                return countryCode;
            }
        }
        return null;
    }
    
    @Nullable
    public static CountryCode fromName(final String name) {
        for (final CountryCode countryCode : CountryCode.VALUES) {
            for (final String countryCodeName : countryCode.getNames()) {
                if (countryCodeName.equalsIgnoreCase(name)) {
                    return countryCode;
                }
            }
        }
        return null;
    }
    
    public String getPrimaryName() {
        return this.names[0];
    }
    
    public String[] getNames() {
        return this.names;
    }
    
    public Icon getIcon() {
        return this.icon;
    }
    
    public String getTranslationKey() {
        return this.translationKey;
    }
    
    public Component getDisplayName() {
        return (Component)(I18n.has(this.translationKey) ? Component.translatable(this.translationKey, new Component[0]) : Component.text(this.getPrimaryName()));
    }
    
    static {
        VALUES = values();
    }
}
