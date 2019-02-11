package com.boonsiri.final_4;

public class MyFFT {

    private int n, m;
    private float[] cos;
    private float[] sin;
    private float[] somFFT;
    private float[] Som;
    private float fs = 44100;
    private float[] f;
    private float[] respfreq;
    private float[] respFreq;
    private static float[][] note = new float[12][1986];
    private float[] S1 = new float[12];
    private static float[][] chord = new float[48][12];
    private float[] S2 = new float[48];
    // private static String[] nomechord = { "C M", "C m", "C aum", "C dim",
    // "C# M", "C# m", "C# aum", "C# dim", "D M", "D m", "D aum", "D dim",
    // "Eb M", "Eb m", "Eb aum", "Eb dim", "E M", "E m", "E aum", "E dim",
    // "F M", "F m", "F aum", "F dim", "F# M", "F# m", "F# aum", "F# dim",
    // "G M", "G m", "G aum", "G dim", "G# M", "G# m", "G# aum", "G# dim",
    // "A M", "A m", "A aum", "A dim", "Bb M", "Bb m", "Bb aum", "Bb dim",
    // "B M", "B m", "B aum", "B dim" };
    private static String[] nomechord = { "F", "F m", "F aum", "F dim", "F#",
            "F# m", "F# aum", "F# dim", "G", "G m", "G aum", "G dim", "G#",
            "G# m", "G# aum", "G# dim", "A", "A m", "A aum", "A dim", "Bb",
            "Bb m", "Bb aum", "Bb dim", "B", "B m", "B aum", "B dim", "C",
            "C m", "C aum", "C dim", "C#", "C# m", "C# aum", "C# dim", "D",
            "D m", "D aum", "D dim", "Eb", "Eb m", "Eb aum", "Eb dim", "E",
            "E m", "E aum", "E dim" };
    private String Acorde;
    private byte[] songInByte;
    private float[] songInFloat;
    private float energy;

    public MyFFT() {

        for (int linha = 0; linha < 12; linha++) {
            for (int coluna = 0; coluna < 1986; coluna++) {
                note[linha][coluna] = 0;
            }
        }

        float g1 = (float) 0.01;
        float g2 = (float) 0.05;
        float g3 = (float) 0.1;
        float g4 = (float) 0.4;
        float g5 = (float) 0.8;
        float g6 = 1;
        int i ;

        /** Linha[0] = Dó */
        note[0][60] = g1;
        note[0][61] = g2;
        note[0][62] = g3;
        note[0][63] = g4;
        note[0][64] = g5;
        note[0][65] = g6;
        note[0][66] = g5;
        note[0][67] = g4;
        note[0][68] = g3;
        note[0][69] = g2;
        note[0][70] = g1;

        note[0][126] = g1;
        note[0][127] = g2;
        note[0][128] = g3;
        note[0][129] = g4;
        note[0][130] = g5;
        note[0][131] = g6;
        note[0][132] = g5;
        note[0][133] = g4;
        note[0][134] = g3;
        note[0][135] = g2;
        note[0][136] = g1;

        note[0][256] = g1;
        note[0][257] = g2;
        note[0][258] = g3;
        note[0][259] = g4;
        note[0][260] = g5;
        note[0][261] = g6;
        note[0][262] = g5;
        note[0][263] = g4;
        note[0][264] = g3;
        note[0][265] = g2;
        note[0][266] = g1;

        note[0][518] = g1;
        note[0][519] = g2;
        note[0][520] = g3;
        note[0][521] = g4;
        note[0][522] = g5;
        note[0][523] = g6;
        note[0][524] = g5;
        note[0][525] = g4;
        note[0][526] = g3;
        note[0][527] = g2;
        note[0][528] = g1;

        note[0][1041] = g1;
        note[0][1042] = g2;
        note[0][1043] = g3;
        note[0][1044] = g4;
        note[0][1045] = g5;
        note[0][1046] = g6;
        note[0][1047] = g5;
        note[0][1048] = g4;
        note[0][1049] = g3;
        note[0][1050] = g2;
        note[0][1051] = g1;

        /** Linha[1] = Dó# */
        note[1][64] = g1;
        note[1][65] = g2;
        note[1][66] = g3;
        note[1][67] = g4;
        note[1][68] = g5;
        note[1][69] = g6;
        note[1][70] = g5;
        note[1][71] = g4;
        note[1][72] = g3;
        note[1][73] = g2;
        note[1][74] = g1;

        note[1][133] = g1;
        note[1][134] = g2;
        note[1][135] = g3;
        note[1][136] = g4;
        note[1][137] = g5;
        note[1][138] = g6;
        note[1][139] = g5;
        note[1][140] = g4;
        note[1][141] = g3;
        note[1][142] = g2;
        note[1][143] = g1;

        note[1][272] = g1;
        note[1][273] = g2;
        note[1][274] = g3;
        note[1][275] = g4;
        note[1][276] = g5;
        note[1][277] = g6;
        note[1][278] = g5;
        note[1][279] = g4;
        note[1][280] = g3;
        note[1][281] = g2;
        note[1][282] = g1;

        note[1][549] = g1;
        note[1][550] = g2;
        note[1][551] = g3;
        note[1][552] = g4;
        note[1][553] = g5;
        note[1][554] = g6;
        note[1][555] = g5;
        note[1][556] = g4;
        note[1][557] = g3;
        note[1][558] = g2;
        note[1][559] = g1;

        note[1][1104] = g1;
        note[1][1105] = g2;
        note[1][1106] = g3;
        note[1][1107] = g4;
        note[1][1108] = g5;
        note[1][1109] = g6;
        note[1][1110] = g5;
        note[1][1111] = g4;
        note[1][1112] = g3;
        note[1][1113] = g2;
        note[1][1114] = g1;

        /** Linha[2] = Ré */
        note[2][68] = g1;
        note[2][69] = g2;
        note[2][70] = g3;
        note[2][71] = g4;
        note[2][72] = g5;
        note[2][73] = g6;
        note[2][74] = g5;
        note[2][75] = g4;
        note[2][76] = g3;
        note[2][77] = g2;
        note[2][78] = g1;

        note[2][142] = g1;
        note[2][143] = g2;
        note[2][144] = g3;
        note[2][145] = g4;
        note[2][146] = g5;
        note[2][147] = g6;
        note[2][148] = g5;
        note[2][149] = g4;
        note[2][150] = g3;
        note[2][151] = g2;
        note[2][152] = g1;

        note[2][289] = g1;
        note[2][290] = g2;
        note[2][291] = g3;
        note[2][292] = g4;
        note[2][293] = g5;
        note[2][294] = g6;
        note[2][295] = g5;
        note[2][296] = g4;
        note[2][297] = g3;
        note[2][298] = g2;
        note[2][299] = g1;

        note[2][582] = g1;
        note[2][583] = g2;
        note[2][584] = g3;
        note[2][585] = g4;
        note[2][586] = g5;
        note[2][587] = g6;
        note[2][588] = g5;
        note[2][589] = g4;
        note[2][590] = g3;
        note[2][591] = g2;
        note[2][592] = g1;

        note[2][1169] = g1;
        note[2][1170] = g2;
        note[2][1171] = g3;
        note[2][1172] = g4;
        note[2][1173] = g5;
        note[2][1174] = g6;
        note[2][1175] = g5;
        note[2][1176] = g4;
        note[2][1177] = g3;
        note[2][1178] = g2;
        note[2][1179] = g1;

        /** Linha[3] = Ré# */
        note[3][73] = g1;
        note[3][74] = g2;
        note[3][75] = g3;
        note[3][76] = g4;
        note[3][77] = g5;
        note[3][78] = g6;
        note[3][79] = g5;
        note[3][80] = g4;
        note[3][81] = g3;
        note[3][82] = g2;
        note[3][83] = g1;

        note[3][140] = g1;
        note[3][141] = g2;
        note[3][152] = g3;
        note[3][153] = g4;
        note[3][154] = g5;
        note[3][155] = g6;
        note[3][156] = g5;
        note[3][157] = g4;
        note[3][158] = g3;
        note[3][159] = g2;
        note[3][160] = g1;

        note[3][306] = g1;
        note[3][307] = g2;
        note[3][308] = g3;
        note[3][309] = g4;
        note[3][310] = g5;
        note[3][311] = g6;
        note[3][312] = g5;
        note[3][313] = g4;
        note[3][314] = g3;
        note[3][315] = g2;
        note[3][316] = g1;

        note[3][617] = g1;
        note[3][618] = g2;
        note[3][619] = g3;
        note[3][620] = g4;
        note[3][621] = g5;
        note[3][622] = g6;
        note[3][623] = g5;
        note[3][624] = g4;
        note[3][625] = g3;
        note[3][626] = g2;
        note[3][627] = g1;

        note[3][1240] = g1;
        note[3][1241] = g2;
        note[3][1242] = g3;
        note[3][1243] = g4;
        note[3][1244] = g5;
        note[3][1245] = g6;
        note[3][1246] = g5;
        note[3][1247] = g4;
        note[3][1248] = g3;
        note[3][1249] = g2;
        note[3][1250] = g1;

        /** Linha[4] = Mi */
        note[4][77] = g1;
        note[4][78] = g2;
        note[4][79] = g3;
        note[4][80] = g4;
        note[4][81] = g5;
        note[4][82] = g6;
        note[4][83] = g5;
        note[4][84] = g4;
        note[4][85] = g3;
        note[4][86] = g2;
        note[4][87] = g1;

        note[4][160] = g1;
        note[4][161] = g2;
        note[4][162] = g3;
        note[4][163] = g4;
        note[4][164] = g5;
        note[4][165] = g6;
        note[4][166] = g5;
        note[4][167] = g4;
        note[4][168] = g3;
        note[4][169] = g2;
        note[4][170] = g1;

        note[4][325] = g1;
        note[4][326] = g2;
        note[4][327] = g3;
        note[4][328] = g4;
        note[4][329] = g5;
        note[4][330] = g6;
        note[4][331] = g5;
        note[4][332] = g4;
        note[4][333] = g3;
        note[4][334] = g2;
        note[4][335] = g1;

        note[4][655] = g1;
        note[4][656] = g2;
        note[4][657] = g3;
        note[4][658] = g4;
        note[4][659] = g5;
        note[4][660] = g6;
        note[4][661] = g5;
        note[4][662] = g4;
        note[4][663] = g3;
        note[4][664] = g2;
        note[4][665] = g1;

        note[4][1313] = g1;
        note[4][1314] = g2;
        note[4][1315] = g3;
        note[4][1316] = g4;
        note[4][1317] = g5;
        note[4][1318] = g6;
        note[4][1319] = g5;
        note[4][1320] = g4;
        note[4][1321] = g3;
        note[4][1322] = g2;
        note[4][1323] = g1;

        /** Linha[5] = Fá */
        note[5][82] = g1;
        note[5][83] = g2;
        note[5][84] = g3;
        note[5][85] = g4;
        note[5][86] = g5;
        note[5][87] = g6;
        note[5][88] = g5;
        note[5][89] = g4;
        note[5][90] = g3;
        note[5][91] = g2;
        note[5][92] = g1;

        note[5][170] = g1;
        note[5][171] = g2;
        note[5][172] = g3;
        note[5][173] = g4;
        note[5][174] = g5;
        note[5][175] = g6;
        note[5][176] = g5;
        note[5][177] = g4;
        note[5][178] = g3;
        note[5][189] = g2;
        note[5][190] = g1;

        note[5][344] = g1;
        note[5][345] = g2;
        note[5][346] = g3;
        note[5][347] = g4;
        note[5][348] = g5;
        note[5][349] = g6;
        note[5][350] = g5;
        note[5][351] = g4;
        note[5][352] = g3;
        note[5][353] = g2;
        note[5][354] = g1;

        note[5][693] = g1;
        note[5][694] = g2;
        note[5][695] = g3;
        note[5][696] = g4;
        note[5][697] = g5;
        note[5][698] = g6;
        note[5][699] = g5;
        note[5][700] = g4;
        note[5][701] = g3;
        note[5][702] = g2;
        note[5][703] = g1;

        note[5][1392] = g1;
        note[5][1393] = g2;
        note[5][1394] = g3;
        note[5][1395] = g4;
        note[5][1396] = g5;
        note[5][1397] = g6;
        note[5][1398] = g5;
        note[5][1399] = g4;
        note[5][1400] = g3;
        note[5][1401] = g2;
        note[5][1402] = g1;

        /** Linha[6] = Fá# */
        note[6][88] = g1;
        note[6][89] = g2;
        note[6][90] = g3;
        note[6][91] = g4;
        note[6][92] = g5;
        note[6][93] = g6;
        note[6][94] = g5;
        note[6][95] = g4;
        note[6][96] = g3;
        note[6][97] = g2;
        note[6][98] = g1;

        note[6][180] = g1;
        note[6][181] = g2;
        note[6][182] = g3;
        note[6][183] = g4;
        note[6][184] = g5;
        note[6][185] = g6;
        note[6][186] = g5;
        note[6][187] = g4;
        note[6][188] = g3;
        note[6][189] = g2;
        note[6][190] = g1;

        note[6][365] = g1;
        note[6][366] = g2;
        note[6][367] = g3;
        note[6][368] = g4;
        note[6][369] = g5;
        note[6][370] = g6;
        note[6][371] = g5;
        note[6][372] = g4;
        note[6][373] = g3;
        note[6][374] = g2;
        note[6][375] = g1;

        note[6][735] = g1;
        note[6][736] = g2;
        note[6][737] = g3;
        note[6][738] = g4;
        note[6][739] = g5;
        note[6][740] = g6;
        note[6][741] = g5;
        note[6][742] = g4;
        note[6][743] = g3;
        note[6][744] = g2;
        note[6][745] = g1;

        note[6][1475] = g1;
        note[6][1476] = g2;
        note[6][1477] = g3;
        note[6][1478] = g4;
        note[6][1479] = g5;
        note[6][1480] = g6;
        note[6][1481] = g5;
        note[6][1482] = g4;
        note[6][1483] = g3;
        note[6][1484] = g2;
        note[6][1485] = g1;

        /** Linha[7] = Sol */
        note[7][93] = g1;
        note[7][94] = g2;
        note[7][95] = g3;
        note[7][96] = g4;
        note[7][97] = g5;
        note[7][98] = g6;
        note[7][99] = g5;
        note[7][100] = g4;
        note[7][101] = g3;
        note[7][102] = g2;
        note[7][103] = g1;

        note[7][191] = g1;
        note[7][192] = g2;
        note[7][193] = g3;
        note[7][194] = g4;
        note[7][195] = g5;
        note[7][196] = g6;
        note[7][197] = g5;
        note[7][198] = g4;
        note[7][199] = g3;
        note[7][200] = g2;
        note[7][201] = g1;

        note[7][387] = g1;
        note[7][388] = g2;
        note[7][389] = g3;
        note[7][390] = g4;
        note[7][391] = g5;
        note[7][392] = g6;
        note[7][393] = g5;
        note[7][394] = g4;
        note[7][395] = g3;
        note[7][396] = g2;
        note[7][397] = g1;

        note[7][779] = g1;
        note[7][780] = g2;
        note[7][781] = g3;
        note[7][782] = g4;
        note[7][783] = g5;
        note[7][784] = g6;
        note[7][785] = g5;
        note[7][786] = g4;
        note[7][787] = g3;
        note[7][788] = g2;
        note[7][789] = g1;

        note[7][1563] = g1;
        note[7][1564] = g2;
        note[7][1565] = g3;
        note[7][1566] = g4;
        note[7][1567] = g5;
        note[7][1568] = g6;
        note[7][1569] = g5;
        note[7][1570] = g4;
        note[7][1571] = g3;
        note[7][1572] = g2;
        note[7][1573] = g1;

        /** Linha[8] = Sol# */
        note[8][101] = g1;
        note[8][100] = g2;
        note[8][101] = g3;
        note[8][102] = g4;
        note[8][103] = g5;
        note[8][104] = g6;
        note[8][105] = g5;
        note[8][106] = g4;
        note[8][107] = g3;
        note[8][108] = g2;
        note[8][109] = g1;

        note[8][203] = g1;
        note[8][204] = g2;
        note[8][205] = g3;
        note[8][206] = g4;
        note[8][207] = g5;
        note[8][208] = g6;
        note[8][209] = g5;
        note[8][210] = g4;
        note[8][211] = g3;
        note[8][212] = g2;
        note[8][213] = g1;

        note[8][410] = g1;
        note[8][411] = g2;
        note[8][412] = g3;
        note[8][413] = g4;
        note[8][414] = g5;
        note[8][415] = g6;
        note[8][416] = g5;
        note[8][417] = g4;
        note[8][418] = g3;
        note[8][419] = g2;
        note[8][420] = g1;

        note[8][825] = g1;
        note[8][826] = g2;
        note[8][827] = g3;
        note[8][828] = g4;
        note[8][829] = g5;
        note[8][830] = g6;
        note[8][831] = g5;
        note[8][832] = g4;
        note[8][833] = g3;
        note[8][834] = g2;
        note[8][835] = g1;

        note[8][1656] = g1;
        note[8][1657] = g2;
        note[8][1658] = g3;
        note[8][1659] = g4;
        note[8][1660] = g5;
        note[8][1661] = g6;
        note[8][1662] = g5;
        note[8][1663] = g4;
        note[8][1664] = g3;
        note[8][1665] = g2;
        note[8][1666] = g1;

        /** Linha[9] = La */
        note[9][105] = g1;
        note[9][106] = g2;
        note[9][107] = g3;
        note[9][108] = g4;
        note[9][109] = g5;
        note[9][110] = g6;
        note[9][111] = g5;
        note[9][112] = g4;
        note[9][113] = g3;
        note[9][114] = g2;
        note[9][115] = g1;

        note[9][215] = g1;
        note[9][216] = g2;
        note[9][217] = g3;
        note[9][218] = g4;
        note[9][219] = g5;
        note[9][220] = g6;
        note[9][221] = g5;
        note[9][222] = g4;
        note[9][223] = g3;
        note[9][224] = g2;
        note[9][225] = g1;

        note[9][435] = g1;
        note[9][436] = g2;
        note[9][437] = g3;
        note[9][438] = g4;
        note[9][439] = g5;
        note[9][440] = g6;
        note[9][441] = g5;
        note[9][442] = g4;
        note[9][443] = g3;
        note[9][444] = g2;
        note[9][445] = g1;

        note[9][875] = g1;
        note[9][876] = g2;
        note[9][877] = g3;
        note[9][878] = g4;
        note[9][879] = g5;
        note[9][880] = g6;
        note[9][881] = g5;
        note[9][882] = g4;
        note[9][883] = g3;
        note[9][884] = g2;
        note[9][885] = g1;

        note[9][1755] = g1;
        note[9][1756] = g2;
        note[9][1757] = g3;
        note[9][1758] = g4;
        note[9][1759] = g5;
        note[9][1760] = g6;
        note[9][1761] = g5;
        note[9][1762] = g4;
        note[9][1763] = g3;
        note[9][1764] = g2;
        note[9][1765] = g1;

        /** Linha[10] = La# */
        note[10][111] = g1;
        note[10][112] = g2;
        note[10][113] = g3;
        note[10][114] = g4;
        note[10][115] = g5;
        note[10][116] = g6;
        note[10][117] = g5;
        note[10][118] = g4;
        note[10][119] = g3;
        note[10][120] = g2;
        note[10][121] = g1;

        note[10][228] = g1;
        note[10][229] = g2;
        note[10][230] = g3;
        note[10][231] = g4;
        note[10][232] = g5;
        note[10][233] = g6;
        note[10][234] = g5;
        note[10][235] = g4;
        note[10][236] = g3;
        note[10][237] = g2;
        note[10][238] = g1;

        note[10][461] = g1;
        note[10][462] = g2;
        note[10][463] = g3;
        note[10][464] = g4;
        note[10][465] = g5;
        note[10][466] = g6;
        note[10][467] = g5;
        note[10][468] = g4;
        note[10][469] = g3;
        note[10][470] = g2;
        note[10][471] = g1;

        note[10][927] = g1;
        note[10][928] = g2;
        note[10][929] = g3;
        note[10][930] = g4;
        note[10][931] = g5;
        note[10][932] = g6;
        note[10][933] = g5;
        note[10][934] = g4;
        note[10][935] = g3;
        note[10][936] = g2;
        note[10][937] = g1;

        note[10][1859] = g1;
        note[10][1860] = g2;
        note[10][1861] = g3;
        note[10][1862] = g4;
        note[10][1863] = g5;
        note[10][1864] = g6;
        note[10][1865] = g5;
        note[10][1866] = g4;
        note[10][1867] = g3;
        note[10][1868] = g2;
        note[10][1869] = g1;

        /** Linha[11] = Si */
        note[11][119] = g1;
        note[11][120] = g2;
        note[11][121] = g3;
        note[11][122] = g4;
        note[11][123] = g5;
        note[11][124] = g6;
        note[11][125] = g5;
        note[11][126] = g4;
        note[11][127] = g3;
        note[11][128] = g2;
        note[11][129] = g1;

        note[11][242] = g1;
        note[11][243] = g2;
        note[11][244] = g3;
        note[11][245] = g4;
        note[11][246] = g5;
        note[11][247] = g6;
        note[11][248] = g5;
        note[11][249] = g4;
        note[11][250] = g3;
        note[11][251] = g2;
        note[11][252] = g1;

        note[11][489] = g1;
        note[11][490] = g2;
        note[11][491] = g3;
        note[11][492] = g4;
        note[11][493] = g5;
        note[11][494] = g6;
        note[11][495] = g5;
        note[11][496] = g4;
        note[11][497] = g3;
        note[11][498] = g2;
        note[11][499] = g1;

        note[11][983] = g1;
        note[11][984] = g2;
        note[11][985] = g3;
        note[11][986] = g4;
        note[11][987] = g5;
        note[11][988] = g6;
        note[11][989] = g5;
        note[11][990] = g4;
        note[11][991] = g3;
        note[11][992] = g2;
        note[11][993] = g1;

        note[11][1971] = g1;
        note[11][1972] = g2;
        note[11][1973] = g3;
        note[11][1974] = g4;
        note[11][1975] = g5;
        note[11][1976] = g6;
        note[11][1977] = g5;
        note[11][1978] = g4;
        note[11][1979] = g3;
        note[11][1980] = g2;
        note[11][1981] = g1;

        /**
         * BD de chord
         */
        for (int linha = 0; linha < 48; linha++) {
            for (int coluna = 0; coluna < 12; coluna++) {
                chord[linha][coluna] = 0;
            }
        }

        // chord de C
        /* CM */
        chord[0][0] = 1;
        chord[0][4] = 1;
        chord[0][7] = 1;
        /* Cm */
        chord[1][0] = 1;
        chord[1][3] = 1;
        chord[1][7] = 1;
        /* Caum */
        chord[2][0] = 1;
        chord[2][4] = 1;
        chord[2][8] = 1;
        /* Cdim */
        chord[3][0] = 1;
        chord[3][3] = 1;
        chord[3][6] = 1;
        // chord de C#
        /* C#M */
        chord[4][1] = 1;
        chord[4][5] = 1;
        chord[4][8] = 1;
        /* C#m */
        chord[5][1] = 1;
        chord[5][4] = 1;
        chord[5][8] = 1;
        /* C#aum */
        chord[6][1] = 1;
        chord[6][5] = 1;
        chord[6][9] = 1;
        /* C#dim */
        chord[7][1] = 1;
        chord[7][4] = 1;
        chord[7][7] = 1;
        // chord de D
        /* DM */
        chord[8][2] = 1;
        chord[8][6] = 1;
        chord[8][9] = 1;
        /* Dm */
        chord[9][2] = 1;
        chord[9][5] = 1;
        chord[9][9] = 1;
        /* Daum */
        chord[10][2] = 1;
        chord[10][6] = 1;
        chord[10][10] = 1;
        /* Ddim */
        chord[11][2] = 1;
        chord[11][5] = 1;
        chord[11][8] = 1;
        // chord de D#
        /* D#M */
        chord[12][3] = 1;
        chord[12][7] = 1;
        chord[12][10] = 1;
        /* D#m */
        chord[13][3] = 1;
        chord[13][6] = 1;
        chord[13][10] = 1;
        /* D#aum */
        chord[14][3] = 1;
        chord[14][7] = 1;
        chord[14][11] = 1;
        /* D#dim */
        chord[15][3] = 1;
        chord[15][6] = 1;
        chord[15][9] = 1;
        // chord de E
        /* EM */
        chord[16][4] = 1;
        chord[16][8] = 1;
        chord[16][11] = 1;
        /* Em */
        chord[17][4] = 1;
        chord[17][7] = 1;
        chord[17][11] = 1;
        /* Eaum */
        chord[18][4] = 1;
        chord[18][8] = 1;
        chord[18][0] = 1;
        /* Edim */
        chord[19][4] = 1;
        chord[19][7] = 1;
        chord[19][10] = 1;
        // chord de F
        /* FM */
        chord[20][5] = 1;
        chord[20][9] = 1;
        chord[20][0] = 1;
        /* Fm */
        chord[21][5] = 1;
        chord[21][8] = 1;
        chord[21][0] = 1;
        /* Faum */
        chord[22][5] = 1;
        chord[22][9] = 1;
        chord[22][1] = 1;
        /* Fdim */
        chord[23][5] = 1;
        chord[23][8] = 1;
        chord[23][11] = 1;
        // chord de F#
        /* F#M */
        chord[24][6] = 1;
        chord[24][10] = 1;
        chord[24][1] = 1;
        /* F#m */
        chord[25][6] = 1;
        chord[25][9] = 1;
        chord[25][1] = 1;
        /* F#aum */
        chord[26][6] = 1;
        chord[26][10] = 1;
        chord[26][2] = 1;
        /* F#dim */
        chord[27][6] = 1;
        chord[27][9] = 1;
        chord[27][0] = 1;
        // chord de G
        /* GM */
        chord[28][7] = 1;
        chord[28][11] = 1;
        chord[28][2] = 1;
        /* Gm */
        chord[29][7] = 1;
        chord[29][10] = 1;
        chord[29][2] = 1;
        /* Gaum */
        chord[30][7] = 1;
        chord[30][11] = 1;
        chord[30][3] = 1;
        /* Gdim */
        chord[31][7] = 1;
        chord[31][10] = 1;
        chord[31][1] = 1;
        // chord de G#
        /* G#M */
        chord[32][8] = 1;
        chord[32][0] = 1;
        chord[32][3] = 1;
        /* G#m */
        chord[33][8] = 1;
        chord[33][11] = 1;
        chord[33][3] = 1;
        /* G#aum */
        chord[34][8] = 1;
        chord[34][0] = 1;
        chord[34][4] = 1;
        /* G#dim */
        chord[35][8] = 1;
        chord[35][11] = 1;
        chord[35][2] = 1;
        // chord de A
        /* AM */
        chord[36][9] = 1;
        chord[36][1] = 1;
        chord[36][4] = 1;
        /* Am */
        chord[37][9] = 1;
        chord[37][0] = 1;
        chord[37][4] = 1;
        /* Aaum */
        chord[38][9] = 1;
        chord[38][1] = 1;
        chord[38][5] = 1;
        /* Adim */
        chord[39][9] = 1;
        chord[39][0] = 1;
        chord[39][3] = 1;
        // chord A#
        /* A#M */
        chord[40][10] = 1;
        chord[40][2] = 1;
        chord[40][5] = 1;
        /* A#m */
        chord[41][10] = 1;
        chord[41][1] = 1;
        chord[41][5] = 1;
        /* A#aum */
        chord[42][10] = 1;
        chord[42][2] = 1;
        chord[42][6] = 1;
        /* A#dim */
        chord[43][10] = 1;
        chord[43][1] = 1;
        chord[43][4] = 1;
        // chord B
        /* BM */
        chord[44][11] = 1;
        chord[44][3] = 1;
        chord[44][6] = 1;
        /* Bm */
        chord[45][11] = 1;
        chord[45][2] = 1;
        chord[45][6] = 1;
        /* Baum */
        chord[46][11] = 1;
        chord[46][3] = 1;
        chord[46][7] = 1;
        /* Bdim */
        chord[47][11] = 1;
        chord[47][2] = 1;
        chord[47][5] = 1;

    }

    public void setByteArraySong(byte[] songInByte) {

        this.songInByte = songInByte;

    }

    public void setS1(float[] S1) {
        this.S1 = S1;
    }

    public float[] getsomFFT() {
        return respFreq;
    }

    public float[] getFreq() {
        return f;
    }

    public float getEnergy() {
        return energy;
    }

    public float[] getS1() {
        float maxsongInFloat = 0;
        this.songInFloat = new float[songInByte.length / 2];
        for (int i = 0; i < songInFloat.length; i++) {
            songInFloat[i] = ((songInByte[i * 2] & 0XFF) | (songInByte[i * 2 + 1] << 8)) / 32768.0F;
            if (songInFloat[i] > maxsongInFloat) {
                maxsongInFloat = songInFloat[i];
            }
        }

        float sum = 0;
        for (int i = 0; i < songInFloat.length; i++) {
            songInFloat[i] = songInFloat[i] / maxsongInFloat;
            sum += (songInFloat[i] * songInFloat[i]);
        }
        this.energy = (sum / songInFloat.length);

        System.out.println("Energia total: " + this.energy);

        this.Som = songInFloat;

        this.n = (int) Math.pow(2, (int) (Math.log(Som.length) / Math.log(2)));
        this.m = (int) (Math.log(Som.length) / Math.log(2));

        cos = new float[n / 2];
        sin = new float[n / 2];

        for (int i = 0; i < n / 2; i++) {
            cos[i] = (float) Math.cos(-2 * Math.PI * i / n);
            sin[i] = (float) Math.sin(-2 * Math.PI * i / n);
        }

        int i, j, k, n1, n2, a;
        float c, s, t1, t2;
        float[] x, y;
        x = Som;
        y = new float[x.length];
        for (int l = 0; l < n; l++) {
            y[l] = 0;
        }

        j = 0;
        n2 = n / 2;
        for (i = 1; i < n - 1; i++) {
            n1 = n2;
            while (j >= n1) {
                j = j - n1;
                n1 = n1 / 2;
            }
            j = j + n1;
            if (i < j) {
                t1 = x[i];
                x[i] = x[j];
                x[j] = t1;
                t1 = y[i];
                y[i] = y[j];
                y[j] = t1;
            }
        }
        n1 = 0;
        n2 = 1;
        for (i = 0; i < m; i++) {
            n1 = n2;
            n2 = n2 + n2;
            a = 0;
            for (j = 0; j < n1; j++) {
                c = cos[a];
                s = sin[a];
                a += 1 << (m - i - 1);
                for (k = j; k < n; k = k + n2) {
                    t1 = c * x[k + n1] - s * y[k + n1];
                    t2 = s * x[k + n1] + c * y[k + n1];
                    x[k + n1] = x[k] - t1;
                    y[k + n1] = y[k] - t2;
                    x[k] = x[k] + t1;
                    y[k] = y[k] + t2;
                }
            }
        }

        float MaxFFTSom = 0;
        float[] SomFFT = new float[x.length];
        for (int l = 0; l < x.length; l++) {
            SomFFT[l] = (float) Math.sqrt(x[l] * x[l] + y[l] * y[l]);
            if (SomFFT[l] > MaxFFTSom) {
                MaxFFTSom = SomFFT[l];
            }
        }

        somFFT = new float[Math.round((SomFFT.length) / 2)];
        f = new float[somFFT.length];
        for (int l = 0; l < Math.round((SomFFT.length) / 2); l++) {
            somFFT[l] = SomFFT[l] / MaxFFTSom;
            f[l] = l * fs / n;
        }

        int ll = 0;
        int jl = 0;
        int il = 0;
        float SOMA = 0;
        respfreq = new float[Math.round(f[f.length - 1])];
        while (il < f.length - 1) {
            if (Math.round(f[il]) == Math.round(f[il + 1])) {
                SOMA = somFFT[il + 1] + SOMA;
                jl++;
            } else {
                respfreq[ll] = SOMA / (jl + 1);
                jl = 0;
                SOMA = somFFT[il + 1];
                ll++;
            }
            il++;
        }
        ll = 0;
        jl = 0;
        il = 0;

        respFreq = new float[1986];
        float maxRespFreq = 0;
        for (int l = 0; l < respfreq.length; l++) {
            if (maxRespFreq < respfreq[l]) {
                maxRespFreq = respfreq[l];
            }
        }
        for (int l = 0; l < 1985; l++) {
            respFreq[l + 1] = respfreq[l] / maxRespFreq;
        }

        float[] nota = new float[1986];
        for (int l = 0; l < 12; l++) {

            for (int l2 = 0; l2 < nota.length; l2++) {
                nota[l2] = note[l][l2];
            }

            float somaX = 0;
            float somaY = 0;
            for (int l3 = 0; l3 < respFreq.length; l3++) {
                somaX = somaX + respFreq[l3];
                somaY = somaY + nota[l3];
            }
            float mediaX = somaX / respFreq.length;
            float mediaY = somaY / nota.length;

            float varX = 0;
            float varY = 0;
            float covXY = 0;
            for (int l4 = 0; l4 < respFreq.length; l4++) {
                varX = (float) (respFreq[l4] - mediaX)
                        * (respFreq[l4] - mediaX) + varX;
                varY = (float) (nota[l4] - mediaY) * (nota[l4] - mediaY) + varY;
                covXY = (respFreq[l4] - mediaX) * (nota[l4] - mediaY) + covXY;
            }

            S1[l] = (float) Math.sqrt(Math.pow(
                    (covXY / Math.sqrt(varX * varY)), 2));
            System.out.println("S1:" + S1[l]);
        }

        float min = 1;
        for (int m = 0; m < S1.length; m++) {
            if (S1[m] < min) {
                min = S1[m];
            }
        }
        for (int l = 0; l < S1.length; l++) {
            S1[l] = (S1[l] - min);
        }
        float max = 0;
        for (int l = 0; l < S1.length; l++) {
            if (S1[l] > max) {
                max = S1[l];
            }
        }
        for (int l = 0; l < S1.length; l++) {
            S1[l] = S1[l] / max;
        }
        return S1;
    }
    public float[] getS2() {
        return S2;
    }
    public int getAcorde() {

        float[] acorde = new float[12];
        for (int l = 0; l < 48; l++) {

            for (int l2 = 0; l2 < acorde.length; l2++) {
                acorde[l2] = chord[l][l2];
            }
            float somaX = 0;
            float somaY = 0;
            for (int l3 = 0; l3 < S1.length; l3++) {
                somaX = somaX + S1[l3];
                somaY = somaY + acorde[l3];
            }
            float mediaX = somaX / S1.length;
            float mediaY = somaY / acorde.length;

            float varX = 0;
            float varY = 0;
            float covXY = 0;
            for (int l4 = 0; l4 < S1.length; l4++) {
                varX = (float) (S1[l4] - mediaX) * (S1[l4] - mediaX) + varX;
                varY = (float) (acorde[l4] - mediaY) * (acorde[l4] - mediaY)
                        + varY;
                covXY = (S1[l4] - mediaX) * (acorde[l4] - mediaY) + covXY;
            }

            S2[l] = (float) Math.sqrt(Math.pow(
                    (covXY / Math.sqrt(varX * varY)), 2));
        }

        float maxS2 = 0;
        for (int l = 0; l < S2.length; l++) {
            if (S2[l] > maxS2) {
                maxS2 = S2[l];
            }
        }
        int slotMaxS2 = 0;
        for (int l = 0; l < S2.length; l++) {
            if (S2[l] == maxS2) {
                slotMaxS2 = l;
            }
        }
        Acorde = nomechord[slotMaxS2];
        return slotMaxS2;
    }

}