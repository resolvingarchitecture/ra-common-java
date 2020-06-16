package ra.common;

import java.util.HashMap;
import java.util.Map;

public class Country {
    private static final Map<String, String> countryCodeMap = new HashMap<>();
    static {
        countryCodeMap.put("Afghanistan","AF"); // name=Afghanistan / region=Asia / subregion=Southern Asia
        countryCodeMap.put("Åland Islands","AX"); // name=Åland Islands / region=Europe / subregion=Northern Europe
        countryCodeMap.put("Albania","AL"); // name=Albania / region=Europe / subregion=Southern Europe
        countryCodeMap.put("Algeria","DZ"); // name=Algeria / region=Africa / subregion=Northern Africa
        countryCodeMap.put("American Samoa","AS"); // name=American Samoa / region=Oceania / subregion=Polynesia
        countryCodeMap.put("Andorra", "AD"); // name=Andorra / region=Europe / subregion=Southern Europe
        countryCodeMap.put("Angola", "AO"); // name=Angola / region=Africa / subregion=Middle Africa
        countryCodeMap.put("Anguilla", "AI"); // name=Anguilla / region=Americas / subregion=Caribbean
        countryCodeMap.put("Antigua and Barbuda", "AG"); // name=Antigua and Barbuda / region=Americas / subregion=Caribbean
        countryCodeMap.put("Argentina", "AR"); // name=Argentina / region=Americas / subregion=South America
        countryCodeMap.put("Armenia", "AM"); // name=Armenia / region=Asia / subregion=Western Asia
        countryCodeMap.put("Aruba", "AW"); // name=Aruba / region=Americas / subregion=Caribbean
        countryCodeMap.put("Australia", "AU"); // name=Australia / region=Oceania / subregion=Australia and New Zealand
        countryCodeMap.put("Austria", "AT"); // name=Austria / region=Europe / subregion=Western Europe
        countryCodeMap.put("Azerbaijan", "AZ"); // name=Azerbaijan / region=Asia / subregion=Western Asia
        countryCodeMap.put("Bahamas", "BS"); // name=Bahamas / region=Americas / subregion=Caribbean
        countryCodeMap.put("Bahrain", "BH"); // name=Bahrain / region=Asia / subregion=Western Asia
        countryCodeMap.put("Bangladesh", "BD"); // name=Bangladesh / region=Asia / subregion=Southern Asia
        countryCodeMap.put("Barbados", "BB"); // name=Barbados / region=Americas / subregion=Caribbean
        countryCodeMap.put("Belarus", "BY"); // name=Belarus / region=Europe / subregion=Eastern Europe
        countryCodeMap.put("Belgium", "BE"); // name=Belgium / region=Europe / subregion=Western Europe
        countryCodeMap.put("Belize", "BZ"); // name=Belize / region=Americas / subregion=Central America
        countryCodeMap.put("Benin", "BJ"); // name=Benin / region=Africa / subregion=Western Africa
        countryCodeMap.put("Bermuda", "BM"); // name=Bermuda / region=Americas / subregion=Northern America
        countryCodeMap.put("Bhutan", "BT"); // name=Bhutan / region=Asia / subregion=Southern Asia
        countryCodeMap.put("Bolivia", "BO"); // name=Bolivia (Plurinational State of) / region=Americas / subregion=South America
        countryCodeMap.put("Bonaire", "BQ"); // name=Bonaire, Sint Eustatius and Saba / region=Americas / subregion=Caribbean
        countryCodeMap.put("Bosnia-Herzegovina", "BA"); // name=Bosnia and Herzegovina / region=Europe / subregion=Southern Europe
        countryCodeMap.put("Bosnia and Herzegovina", "BA"); // name=Bosnia and Herzegovina / region=Europe / subregion=Southern Europe
        countryCodeMap.put("Botswana", "BW"); // name=Botswana / region=Africa / subregion=Southern Africa
        countryCodeMap.put("Brazil", "BR"); // name=Brazil / region=Americas / subregion=South America
        countryCodeMap.put("British Indian Ocean Territory", "IO"); // name=British Indian Ocean Territory / region=Africa / subregion=Eastern Africa
        countryCodeMap.put("United States Minor Outlying Islands", "UM"); // name=United States Minor Outlying Islands / region=Americas / subregion=Northern America
        countryCodeMap.put("Virgin Islands (British)", "VG"); // name=Virgin Islands (British) / region=Americas / subregion=Caribbean
        countryCodeMap.put("Virgin Islands (U.S.)", "VI"); // name=Virgin Islands (U.S.) / region=Americas / subregion=Caribbean
        countryCodeMap.put("Brunei", "BN"); // name=Brunei Darussalam / region=Asia / subregion=South-Eastern Asia
        countryCodeMap.put("Brunei Darussalam", "BN"); // name=Brunei Darussalam / region=Asia / subregion=South-Eastern Asia
        countryCodeMap.put("Bulgaria", "BG"); // name=Bulgaria / region=Europe / subregion=Eastern Europe
        countryCodeMap.put("Burkina Faso", "BF"); // name=Burkina Faso / region=Africa / subregion=Western Africa
        countryCodeMap.put("Burundi", "BI"); // name=Burundi / region=Africa / subregion=Eastern Africa
        countryCodeMap.put("Cambodia", "KH"); // name=Cambodia / region=Asia / subregion=South-Eastern Asia
        countryCodeMap.put("Cameroon", "CM"); // name=Cameroon / region=Africa / subregion=Middle Africa
        countryCodeMap.put("Canada", "CA"); // name=Canada / region=Americas / subregion=Northern America
        countryCodeMap.put("Cabo Verde", "CV"); // name=Cabo Verde / region=Africa / subregion=Western Africa
        countryCodeMap.put("Cayman Islands", "KY"); // name=Cayman Islands / region=Americas / subregion=Caribbean
        countryCodeMap.put("Central African Republic", "CF"); // name=Central African Republic / region=Africa / subregion=Middle Africa
        countryCodeMap.put("Chad", "TD"); // name=Chad / region=Africa / subregion=Middle Africa
        countryCodeMap.put("Chile", "CL"); // name=Chile / region=Americas / subregion=South America
        countryCodeMap.put("China", "CN"); // name=China / region=Asia / subregion=Eastern Asia
        countryCodeMap.put("Christmas Island", "CX"); // name=Christmas Island / region=Oceania / subregion=Australia and New Zealand
        countryCodeMap.put("Cocos (Keeling) Islands", "CC"); // name=Cocos (Keeling) Islands / region=Oceania / subregion=Australia and New Zealand
        countryCodeMap.put("Colombia", "CO"); // name=Colombia / region=Americas / subregion=South America
        countryCodeMap.put("Comoros", "KM"); // name=Comoros / region=Africa / subregion=Eastern Africa
        countryCodeMap.put("Congo", "CG"); // name=Congo / region=Africa / subregion=Middle Africa
        countryCodeMap.put("Congo (Republic of the)", "CG"); // name=Congo / region=Africa / subregion=Middle Africa
        countryCodeMap.put("Congo-Brazzaville", "CG"); // name=Congo / region=Africa / subregion=Middle Africa
        countryCodeMap.put("Congo (Democratic Republic of the)", "CD"); // name=Congo (Democratic Republic of the) / region=Africa / subregion=Middle Africa
        countryCodeMap.put("Democratic Republic of Congo", "CD"); // name=Congo (Democratic Republic of the) / region=Africa / subregion=Middle Africa
        countryCodeMap.put("Cook Islands", "CK"); // name=Cook Islands / region=Oceania / subregion=Polynesia
        countryCodeMap.put("Costa Rica", "CR"); // name=Costa Rica / region=Americas / subregion=Central America
        countryCodeMap.put("Croatia", "HR"); // name=Croatia / region=Europe / subregion=Southern Europe
        countryCodeMap.put("Cuba", "CU"); // name=Cuba / region=Americas / subregion=Caribbean
        countryCodeMap.put("Curaçao", "CW"); // name=Curaçao / region=Americas / subregion=Caribbean
        countryCodeMap.put("Cyprus", "CY"); // name=Cyprus / region=Europe / subregion=Southern Europe
        countryCodeMap.put("Czech Republic", "CZ"); // name=Czech Republic / region=Europe / subregion=Eastern Europe
        countryCodeMap.put("Denmark", "DK"); // name=Denmark / region=Europe / subregion=Northern Europe
        countryCodeMap.put("Djibouti", "DJ"); // name=Djibouti / region=Africa / subregion=Eastern Africa
        countryCodeMap.put("Dominica", "DM"); // name=Dominica / region=Americas / subregion=Caribbean
        countryCodeMap.put("Dominican Republic", "DO"); // name=Dominican Republic / region=Americas / subregion=Caribbean
        countryCodeMap.put("Ecuador", "EC"); // name=Ecuador / region=Americas / subregion=South America
        countryCodeMap.put("Egypt", "EG"); // name=Egypt / region=Africa / subregion=Northern Africa
        countryCodeMap.put("El Salvador", "SV"); // name=El Salvador / region=Americas / subregion=Central America
        countryCodeMap.put("Equatorial Guinea", "GQ"); // name=Equatorial Guinea / region=Africa / subregion=Middle Africa
        countryCodeMap.put("Eritrea", "ER"); // name=Eritrea / region=Africa / subregion=Eastern Africa
        countryCodeMap.put("Estonia", "EE"); // name=Estonia / region=Europe / subregion=Northern Europe
        countryCodeMap.put("Eswatini", "SZ"); // name=Eswatini / region=Africa / subregion=Southern Africa
        countryCodeMap.put("Eswatini (Kingdom of)", "SZ"); // name=Eswatini (Kingdom of) / region=Africa / subregion=Southern Africa
        countryCodeMap.put("Ethiopia", "ET"); // name=Ethiopia / region=Africa / subregion=Eastern Africa
        countryCodeMap.put("Falkland Islands (Malvinas)", "FK"); // name=Falkland Islands (Malvinas) / region=Americas / subregion=South America
        countryCodeMap.put("Faroe Islands", "FO"); // name=Faroe Islands / region=Europe / subregion=Northern Europe
        countryCodeMap.put("Fiji", "FJ"); // name=Fiji / region=Oceania / subregion=Melanesia
        countryCodeMap.put("Finland", "FI"); // name=Finland / region=Europe / subregion=Northern Europe
        countryCodeMap.put("France", "FR"); // name=France / region=Europe / subregion=Western Europe
        countryCodeMap.put("French Guiana", "GF"); // name=French Guiana / region=Americas / subregion=South America
        countryCodeMap.put("French Polynesia", "PF"); // name=French Polynesia / region=Oceania / subregion=Polynesia
        countryCodeMap.put("French Southern Territories", "TF"); // name=French Southern Territories / region=Africa / subregion=Southern Africa
        countryCodeMap.put("Gabon", "GA"); // name=Gabon / region=Africa / subregion=Middle Africa
        countryCodeMap.put("Gambia", "GM"); // name=Gambia / region=Africa / subregion=Western Africa
        countryCodeMap.put("Georgia", "GE"); // name=Georgia / region=Asia / subregion=Western Asia
        countryCodeMap.put("Germany", "DE"); // name=Germany / region=Europe / subregion=Western Europe
        countryCodeMap.put("Ghana", "GH"); // name=Ghana / region=Africa / subregion=Western Africa
        countryCodeMap.put("Gibraltar", "GI"); // name=Gibraltar / region=Europe / subregion=Southern Europe
        countryCodeMap.put("Greece", "GR"); // name=Greece / region=Europe / subregion=Southern Europe
        countryCodeMap.put("Greenland", "GL"); // name=Greenland / region=Americas / subregion=Northern America
        countryCodeMap.put("Grenada", "GD"); // name=Grenada / region=Americas / subregion=Caribbean
        countryCodeMap.put("Guadeloupe", "GP"); // name=Guadeloupe / region=Americas / subregion=Caribbean
        countryCodeMap.put("Guam", "GU"); // name=Guam / region=Oceania / subregion=Micronesia
        countryCodeMap.put("Guatemala", "GT"); // name=Guatemala / region=Americas / subregion=Central America
        countryCodeMap.put("Guernsey", "GG"); // name=Guernsey / region=Europe / subregion=Northern Europe
        countryCodeMap.put("Guinea", "GN"); // name=Guinea / region=Africa / subregion=Western Africa
        countryCodeMap.put("Guinea Bissau", "GW"); // name=Guinea-Bissau / region=Africa / subregion=Western Africa
        countryCodeMap.put("Guinea-Bissau", "GW"); // name=Guinea-Bissau / region=Africa / subregion=Western Africa
        countryCodeMap.put("Guyana", "GY"); // name=Guyana / region=Americas / subregion=South America
        countryCodeMap.put("Haiti", "HT"); // name=Haiti / region=Americas / subregion=Caribbean
        countryCodeMap.put("Haïti", "HT"); // name=Haiti / region=Americas / subregion=Caribbean
        countryCodeMap.put("Holy See", "VA"); // name=Holy See / region=Europe / subregion=Southern Europe
        countryCodeMap.put("Honduras", "HN"); // name=Honduras / region=Americas / subregion=Central America
        countryCodeMap.put("Hong Kong", "HK"); // name=Hong Kong / region=Asia / subregion=Eastern Asia
        countryCodeMap.put("Hungary", "HU"); // name=Hungary / region=Europe / subregion=Eastern Europe
        countryCodeMap.put("Iceland", "IS"); // name=Iceland / region=Europe / subregion=Northern Europe
        countryCodeMap.put("India", "IN"); // name=India / region=Asia / subregion=Southern Asia
        countryCodeMap.put("Indonesia", "ID"); // name=Indonesia / region=Asia / subregion=South-Eastern Asia
        countryCodeMap.put("Ivory Coast", "CI"); // name=Côte d'Ivoire / region=Africa / subregion=Western Africa
        countryCodeMap.put("Côte d'Ivoire", "CI"); // name=Côte d'Ivoire / region=Africa / subregion=Western Africa
        countryCodeMap.put("Iran", "IR"); // name=Iran (Islamic Republic of) / region=Asia / subregion=Southern Asia
        countryCodeMap.put("Iran (Islamic Republic of)", "IR"); // name=Iran (Islamic Republic of) / region=Asia / subregion=Southern Asia
        countryCodeMap.put("Iraq", "IQ"); // name=Iraq / region=Asia / subregion=Western Asia
        countryCodeMap.put("Ireland", "IE"); // name=Ireland / region=Europe / subregion=Northern Europe
        countryCodeMap.put("Isle of Man", "IM"); // name=Isle of Man / region=Europe / subregion=Northern Europe
        countryCodeMap.put("Israel", "IL"); // name=Israel / region=Asia / subregion=Western Asia
        countryCodeMap.put("Italy", "IT"); // name=Italy / region=Europe / subregion=Southern Europe
        countryCodeMap.put("Jamaica", "JM"); // name=Jamaica / region=Americas / subregion=Caribbean
        countryCodeMap.put("Japan", "JP"); // name=Japan / region=Asia / subregion=Eastern Asia
        countryCodeMap.put("Jersey", "JE"); // name=Jersey / region=Europe / subregion=Northern Europe
        countryCodeMap.put("Jordan", "JO"); // name=Jordan / region=Asia / subregion=Western Asia
        countryCodeMap.put("Kazakhstan", "KZ"); // name=Kazakhstan / region=Asia / subregion=Central Asia
        countryCodeMap.put("Kenya", "KE"); // name=Kenya / region=Africa / subregion=Eastern Africa
        countryCodeMap.put("Kiribati", "KI"); // name=Kiribati / region=Oceania / subregion=Micronesia
        countryCodeMap.put("Kosovo", "XK"); // name=Kosovo / region=Europe / subregion=Southern Europe
        countryCodeMap.put("Kuwait", "KW"); // name=Kuwait / region=Asia / subregion=Western Asia
        countryCodeMap.put("Kyrgyzstan", "KG"); // name=Kyrgyzstan / region=Asia / subregion=Central Asia
        countryCodeMap.put("Lao People's Democratic Republic", "LA"); // name=Lao People's Democratic Republic / region=Asia / subregion=South-Eastern Asia
        countryCodeMap.put("Laos", "LA"); // name=Lao People's Democratic Republic / region=Asia / subregion=South-Eastern Asia
        countryCodeMap.put("Latvia", "LV"); // name=Latvia / region=Europe / subregion=Northern Europe
        countryCodeMap.put("Lebanon", "LB"); // name=Lebanon / region=Asia / subregion=Western Asia
        countryCodeMap.put("Lesotho", "LS"); // name=Lesotho / region=Africa / subregion=Southern Africa
        countryCodeMap.put("Liberia", "LR"); // name=Liberia / region=Africa / subregion=Western Africa
        countryCodeMap.put("Libya", "LY"); // name=Libya / region=Africa / subregion=Northern Africa
        countryCodeMap.put("Liechtenstein", "LI"); // name=Liechtenstein / region=Europe / subregion=Western Europe
        countryCodeMap.put("Lithuania", "LT"); // name=Lithuania / region=Europe / subregion=Northern Europe
        countryCodeMap.put("Luxembourg", "LU"); // name=Luxembourg / region=Europe / subregion=Western Europe
        countryCodeMap.put("Macao", "MO"); // name=Macao / region=Asia / subregion=Eastern Asia
        countryCodeMap.put("Macedonia", "MK"); // name=Macedonia (the former Yugoslav Republic of) / region=Europe / subregion=Southern Europe
        countryCodeMap.put("Macedonia (the former Yugoslav Republic of)", "MK"); // name=Macedonia (the former Yugoslav Republic of) / region=Europe / subregion=Southern Europe
        countryCodeMap.put("Madagascar", "MG"); // name=Madagascar / region=Africa / subregion=Eastern Africa
        countryCodeMap.put("Malawi", "MW"); // name=Malawi / region=Africa / subregion=Eastern Africa
        countryCodeMap.put("Malaysia", "MY"); // name=Malaysia / region=Asia / subregion=South-Eastern Asia
        countryCodeMap.put("Maldives", "MV"); // name=Maldives / region=Asia / subregion=Southern Asia
        countryCodeMap.put("Mali", "ML"); // name=Mali / region=Africa / subregion=Western Africa
        countryCodeMap.put("Malta", "MT"); // name=Malta / region=Europe / subregion=Southern Europe
        countryCodeMap.put("Marshall Islands", "MH"); // name=Marshall Islands / region=Oceania / subregion=Micronesia
        countryCodeMap.put("Martinique", "MQ"); // name=Martinique / region=Americas / subregion=Caribbean
        countryCodeMap.put("Mauritania", "MR"); // name=Mauritania / region=Africa / subregion=Western Africa
        countryCodeMap.put("Mauritius", "MU"); // name=Mauritius / region=Africa / subregion=Eastern Africa
        countryCodeMap.put("Mayotte", "YT"); // name=Mayotte / region=Africa / subregion=Eastern Africa
        countryCodeMap.put("Mexico", "MX"); // name=Mexico / region=Americas / subregion=Central America
        countryCodeMap.put("Micronesia (Federated States of)", "FM"); // name=Micronesia (Federated States of) / region=Oceania / subregion=Micronesia
        countryCodeMap.put("Moldova", "MD"); // name=Moldova (Republic of) / region=Europe / subregion=Eastern Europe
        countryCodeMap.put("Moldova (Republic of)", "MD"); // name=Moldova (Republic of) / region=Europe / subregion=Eastern Europe
        countryCodeMap.put("Monaco", "MC"); // name=Monaco / region=Europe / subregion=Western Europe
        countryCodeMap.put("Mongolia", "MN"); // name=Mongolia / region=Asia / subregion=Eastern Asia
        countryCodeMap.put("Montenegro", "ME"); // name=Montenegro / region=Europe / subregion=Southern Europe
        countryCodeMap.put("Montserrat", "MS"); // name=Montserrat / region=Americas / subregion=Caribbean
        countryCodeMap.put("Morocco", "MA"); // name=Morocco / region=Africa / subregion=Northern Africa
        countryCodeMap.put("Morocco / Western Sahara", "MA"); // name=Morocco / region=Africa / subregion=Northern Africa
        countryCodeMap.put("Mozambique", "MZ"); // name=Mozambique / region=Africa / subregion=Eastern Africa
        countryCodeMap.put("Myanmar", "MM"); // name=Myanmar / region=Asia / subregion=South-Eastern Asia
        countryCodeMap.put("Namibia", "NA"); // name=Namibia / region=Africa / subregion=Southern Africa
        countryCodeMap.put("Nauru", "NR"); // name=Nauru / region=Oceania / subregion=Micronesia
        countryCodeMap.put("Nepal", "NP"); // name=Nepal / region=Asia / subregion=Southern Asia
        countryCodeMap.put("Netherlands", "NL"); // name=Netherlands / region=Europe / subregion=Western Europe
        countryCodeMap.put("New Caledonia", "NC"); // name=New Caledonia / region=Oceania / subregion=Melanesia
        countryCodeMap.put("New Zealand", "NZ"); // name=New Zealand / region=Oceania / subregion=Australia and New Zealand
        countryCodeMap.put("Nicaragua", "NI"); // name=Nicaragua / region=Americas / subregion=Central America
        countryCodeMap.put("Niger", "NE"); // name=Niger / region=Africa / subregion=Western Africa
        countryCodeMap.put("Nigeria", "NG"); // name=Nigeria / region=Africa / subregion=Western Africa
        countryCodeMap.put("Niue", "NU"); // name=Niue / region=Oceania / subregion=Polynesia
        countryCodeMap.put("Norfolk Island", "NF"); // name=Norfolk Island / region=Oceania / subregion=Australia and New Zealand
        countryCodeMap.put("Northern Cyprus", "NY"); // name=Northern Cyprus / region=Europe / subregion=Southern Europe
        countryCodeMap.put("North Korea", "KP"); // name=Korea (Democratic People's Republic of) / region=Asia / subregion=Eastern Asia
        countryCodeMap.put("Korea (Democratic People's Republic of)", "KP"); // name=Korea (Democratic People's Republic of) / region=Asia / subregion=Eastern Asia
        countryCodeMap.put("Northern Mariana Islands", "MP"); // name=Northern Mariana Islands / region=Oceania / subregion=Micronesia
        countryCodeMap.put("North Macedonia", "NM"); // name=Macedonia (the former Yugoslav Republic of) / region=Europe / subregion=Southern Europe
        countryCodeMap.put("North Macedonia (Republic of)", "NM"); // name=Macedonia (the former Yugoslav Republic of) / region=Europe / subregion=Southern Europe
        countryCodeMap.put("Norway", "NO"); // name=Norway / region=Europe / subregion=Northern Europe
        countryCodeMap.put("Oman", "OM"); // name=Oman / region=Asia / subregion=Western Asia
        countryCodeMap.put("Pakistan", "PK"); // name=Pakistan / region=Asia / subregion=Southern Asia
        countryCodeMap.put("Palau", "PW"); // name=Palau / region=Oceania / subregion=Micronesia
        countryCodeMap.put("Palestine, State of", "PS"); // name=Palestine, State of / region=Asia / subregion=Western Asia
        countryCodeMap.put("Panama", "PA"); // name=Panama / region=Americas / subregion=Central America
        countryCodeMap.put("Papua New Guinea", "PG"); // name=Papua New Guinea / region=Oceania / subregion=Melanesia
        countryCodeMap.put("Paraguay", "PY"); // name=Paraguay / region=Americas / subregion=South America
        countryCodeMap.put("Peru", "PE"); // name=Peru / region=Americas / subregion=South America
        countryCodeMap.put("Philippines", "PH"); // name=Philippines / region=Asia / subregion=South-Eastern Asia
        countryCodeMap.put("Pitcairn", "PN"); // name=Pitcairn / region=Oceania / subregion=Polynesia
        countryCodeMap.put("Poland", "PL"); // name=Poland / region=Europe / subregion=Eastern Europe
        countryCodeMap.put("Portugal", "PT"); // name=Portugal / region=Europe / subregion=Southern Europe
        countryCodeMap.put("Puerto Rico", "PR"); // name=Puerto Rico / region=Americas / subregion=Caribbean
        countryCodeMap.put("Qatar", "QA"); // name=Qatar / region=Asia / subregion=Western Asia
        countryCodeMap.put("Republic of Kosovo", "XK"); // name=Republic of Kosovo / region=Europe / subregion=Eastern Europe
        countryCodeMap.put("Réunion", "RE"); // name=Réunion / region=Africa / subregion=Eastern Africa
        countryCodeMap.put("Romania", "RO"); // name=Romania / region=Europe / subregion=Eastern Europe
        countryCodeMap.put("Russia", "RU"); // name=Russian Federation / region=Europe / subregion=Eastern Europe
        countryCodeMap.put("Russian Federation", "RU"); // name=Russian Federation / region=Europe / subregion=Eastern Europe
        countryCodeMap.put("Rwanda", "RW"); // name=Rwanda / region=Africa / subregion=Eastern Africa
        countryCodeMap.put("Saint Barthélemy", "BL"); // name=Saint Barthélemy / region=Americas / subregion=Caribbean
        countryCodeMap.put("Saint Helena, Ascension and Tristan da Cunha", "SH"); // name=Saint Helena, Ascension and Tristan da Cunha / region=Africa / subregion=Western Africa
        countryCodeMap.put("Saint Kitts and Nevis", "KN"); // name=Saint Kitts and Nevis / region=Americas / subregion=Caribbean
        countryCodeMap.put("Saint Lucia", "LC"); // name=Saint Lucia / region=Americas / subregion=Caribbean
        countryCodeMap.put("Saint Martin", "MF"); // name=Saint Martin (French part) / region=Americas / subregion=Caribbean
        countryCodeMap.put("Saint Martin (French)", "MF"); // name=Saint Martin (French part) / region=Americas / subregion=Caribbean
        countryCodeMap.put("Saint Martin (French part)", "MF"); // name=Saint Martin (French part) / region=Americas / subregion=Caribbean
        countryCodeMap.put("Saint Pierre and Miquelon", "PM"); // name=Saint Pierre and Miquelon / region=Americas / subregion=Northern America
        countryCodeMap.put("Saint Vincent and the Grenadines", "VC"); // name=Saint Vincent and the Grenadines / region=Americas / subregion=Caribbean
        countryCodeMap.put("Samoa", "WS"); // name=Samoa / region=Oceania / subregion=Polynesia
        countryCodeMap.put("San Marino", "SM"); // name=San Marino / region=Europe / subregion=Southern Europe
        countryCodeMap.put("Sao Tome and Principe", "ST"); // name=Sao Tome and Principe / region=Africa / subregion=Middle Africa
        countryCodeMap.put("Saudi Arabia", "SA"); // name=Saudi Arabia / region=Asia / subregion=Western Asia
        countryCodeMap.put("Senegal", "SN"); // name=Senegal / region=Africa / subregion=Western Africa
        countryCodeMap.put("Serbia", "RS"); // name=Serbia / region=Europe / subregion=Southern Europe
        countryCodeMap.put("Seychelles", "SC"); // name=Seychelles / region=Africa / subregion=Eastern Africa
        countryCodeMap.put("Sierra Leone", "SL"); // name=Sierra Leone / region=Africa / subregion=Western Africa
        countryCodeMap.put("Singapore", "SG"); // name=Singapore / region=Asia / subregion=South-Eastern Asia
        countryCodeMap.put("Sint Maarten", "SX"); // name=Sint Maarten (Dutch part) / region=Americas / subregion=Caribbean
        countryCodeMap.put("Sint Maarten (Dutch)", "SX"); // name=Sint Maarten (Dutch part) / region=Americas / subregion=Caribbean
        countryCodeMap.put("Sint Maarten (Dutch part)", "SX"); // name=Sint Maarten (Dutch part) / region=Americas / subregion=Caribbean
        countryCodeMap.put("Slovakia", "SK"); // name=Slovakia / region=Europe / subregion=Eastern Europe
        countryCodeMap.put("Slovenia", "SI"); // name=Slovenia / region=Europe / subregion=Southern Europe
        countryCodeMap.put("Solomon Islands", "SB"); // name=Solomon Islands / region=Oceania / subregion=Melanesia
        countryCodeMap.put("Somalia", "SO"); // name=Somalia / region=Africa / subregion=Eastern Africa
        countryCodeMap.put("South Africa", "ZA"); // name=South Africa / region=Africa / subregion=Southern Africa
        countryCodeMap.put("South Georgia", "GS"); // name=South Georgia and the South Sandwich Islands / region=Americas / subregion=South America
        countryCodeMap.put("South Georgia and the South Sandwich Islands", "GS"); // name=South Georgia and the South Sandwich Islands / region=Americas / subregion=South America
        countryCodeMap.put("South Korea", "KR"); // name=Korea (Republic of) / region=Asia / subregion=Eastern Asia
        countryCodeMap.put("Korea (Republic of)", "KR"); // name=Korea (Republic of) / region=Asia / subregion=Eastern Asia
        countryCodeMap.put("South Sudan", "SS"); // name=South Sudan / region=Africa / subregion=Middle Africa
        countryCodeMap.put("Spain", "ES"); // name=Spain / region=Europe / subregion=Southern Europe
        countryCodeMap.put("Sri Lanka", "LK"); // name=Sri Lanka / region=Asia / subregion=Southern Asia
        countryCodeMap.put("Sudan", "SD"); // name=Sudan / region=Africa / subregion=Northern Africa
        countryCodeMap.put("Suriname", "SR"); // name=Suriname / region=Americas / subregion=South America
        countryCodeMap.put("Svalbard and Jan Mayen", "SJ"); // name=Svalbard and Jan Mayen / region=Europe / subregion=Northern Europe
        countryCodeMap.put("Swaziland", "SZ"); // name=Swaziland / region=Africa / subregion=Southern Africa
        countryCodeMap.put("Sweden", "SE"); // name=Sweden / region=Europe / subregion=Northern Europe
        countryCodeMap.put("Switzerland", "CH"); // name=Switzerland / region=Europe / subregion=Western Europe
        countryCodeMap.put("Syria", "SY"); // name=Syrian Arab Republic / region=Asia / subregion=Western Asia
        countryCodeMap.put("Syrian Arab Republic", "SY"); // name=Syrian Arab Republic / region=Asia / subregion=Western Asia
        countryCodeMap.put("Taiwan", "TW"); // name=Taiwan / region=Asia / subregion=Eastern Asia
        countryCodeMap.put("Tajikistan", "TJ"); // name=Tajikistan / region=Asia / subregion=Central Asia
        countryCodeMap.put("Tanzania", "TZ"); // name=Tanzania, United Republic of / region=Africa / subregion=Eastern Africa
        countryCodeMap.put("Tanzania, United Republic of", "TZ"); // name=Tanzania, United Republic of / region=Africa / subregion=Eastern Africa
        countryCodeMap.put("Thailand", "TH"); // name=Thailand / region=Asia / subregion=South-Eastern Asia
        countryCodeMap.put("Timor-Leste", "TL"); // name=Timor-Leste / region=Asia / subregion=South-Eastern Asia
        countryCodeMap.put("Togo", "TG"); // name=Togo / region=Africa / subregion=Western Africa
        countryCodeMap.put("Tokelau", "TK"); // name=Tokelau / region=Oceania / subregion=Polynesia
        countryCodeMap.put("Tonga", "TO"); // name=Tonga / region=Oceania / subregion=Polynesia
        countryCodeMap.put("Trinidad and Tobago", "TT"); // name=Trinidad and Tobago / region=Americas / subregion=Caribbean
        countryCodeMap.put("Tunisia", "TN"); // name=Tunisia / region=Africa / subregion=Northern Africa
        countryCodeMap.put("Turkey", "TR"); // name=Turkey / region=Asia / subregion=Western Asia
        countryCodeMap.put("Turkmenistan", "TM"); // name=Turkmenistan / region=Asia / subregion=Central Asia
        countryCodeMap.put("Turks and Caicos", "TC"); // name=Turks and Caicos Islands / region=Americas / subregion=Caribbean
        countryCodeMap.put("Turks and Caicos Islands", "TC"); // name=Turks and Caicos Islands / region=Americas / subregion=Caribbean
        countryCodeMap.put("Tuvalu", "TV"); // name=Tuvalu / region=Oceania / subregion=Polynesia
        countryCodeMap.put("Uganda", "UG"); // name=Uganda / region=Africa / subregion=Eastern Africa
        countryCodeMap.put("Ukraine", "UA"); // name=Ukraine / region=Europe / subregion=Eastern Europe
        countryCodeMap.put("UAE", "AE"); // name=United Arab Emirates / region=Asia / subregion=Western Asia
        countryCodeMap.put("United Arab Emirates", "AE"); // name=United Arab Emirates / region=Asia / subregion=Western Asia
        countryCodeMap.put("UK", "GB"); // name=United Kingdom of Great Britain and Northern Ireland / region=Europe / subregion=Northern Europe
        countryCodeMap.put("Great Britain", "GB"); // name=United Kingdom of Great Britain and Northern Ireland / region=Europe / subregion=Northern Europe
        countryCodeMap.put("Great Britain and Northern Ireland", "GB"); // name=United Kingdom of Great Britain and Northern Ireland / region=Europe / subregion=Northern Europe
        countryCodeMap.put("United Kingdom", "GB"); // name=United Kingdom of Great Britain and Northern Ireland / region=Europe / subregion=Northern Europe
        countryCodeMap.put("United Kingdom of Great Britain", "GB"); // name=United Kingdom of Great Britain and Northern Ireland / region=Europe / subregion=Northern Europe
        countryCodeMap.put("United Kingdom of Great Britain and Northern Ireland", "GB"); // name=United Kingdom of Great Britain and Northern Ireland / region=Europe / subregion=Northern Europe
        countryCodeMap.put("United States", "US"); // name=United States of America / region=Americas / subregion=Northern America
        countryCodeMap.put("United States of America", "US"); // name=United States of America / region=Americas / subregion=Northern America
        countryCodeMap.put("Uruguay", "UY"); // name=Uruguay / region=Americas / subregion=South America
        countryCodeMap.put("Uzbekistan", "UZ"); // name=Uzbekistan / region=Asia / subregion=Central Asia
        countryCodeMap.put("Vanuatu", "VU"); // name=Vanuatu / region=Oceania / subregion=Melanesia
        countryCodeMap.put("Venezuela", "VE"); // name=Venezuela (Bolivarian Republic of) / region=Americas / subregion=South America
        countryCodeMap.put("Venezuela (Bolivarian Republic of)", "VE"); // name=Venezuela (Bolivarian Republic of) / region=Americas / subregion=South America
        countryCodeMap.put("Viet Nam", "VN"); // name=Viet Nam / region=Asia / subregion=South-Eastern Asia
        countryCodeMap.put("Vietnam", "VN"); // name=Viet Nam / region=Asia / subregion=South-Eastern Asia
        countryCodeMap.put("Wallis and Futuna", "WF"); // name=Wallis and Futuna / region=Oceania / subregion=Polynesia
        countryCodeMap.put("Morocco / Western Sahara", "EH"); // name=Western Sahara / region=Africa / subregion=Northern Africa
        countryCodeMap.put("Western Sahara", "EH"); // name=Western Sahara / region=Africa / subregion=Northern Africa
        countryCodeMap.put("Yemen", "YE"); // name=Yemen / region=Asia / subregion=Western Asia
        countryCodeMap.put("Zambia", "ZM"); // name=Zambia / region=Africa / subregion=Eastern Africa
        countryCodeMap.put("Zimbabwe", "ZW"); // name=Zimbabwe / region=Africa / subregion=Eastern Africa
    }
    public static String lookupCountryCode(String country) {
        return countryCodeMap.get(country);
    }
}
