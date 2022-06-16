package ru.ining.gps.mssqlmappers;

import org.apache.ibatis.annotations.*;
import ru.ining.gps.controllers.MainController;
import ru.ining.gps.controllers.MainController.Names;
import ru.ining.gps.controllers.MainController.Templeate;
import ru.ining.gps.entity.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface UnionMapper {

    @Select("select b.Tcpat, b.Tcpoa, ISNULL(d.Des, '') as Dep, a.Des as Template, ISNULL(p.Sname, '') as Sname, ISNULL(p.Fname, '') as Fname, ISNULL(p.Parname, '') as Parname, b.Strdte, b.Stpdte, b.Qnt, ISNULL(x.Des60, '') as Prt, b.Entdte, b.Actdte, b.Tcpoaext as Tcpoext, x.Des AS Ptndes, b.Amt, y.Des40 AS Brndes, b.Tabnum, b.Exesign, b.Crtpsnsign, b.Crtpsndessign, b.Crtpos, b.Crtsign, b.Agrpsnsign, b.Agrpsndessign, b.Agrpos, b.Agrsign, b.Apppsnsign, b.Apppsndessign, b.Apppos, b.Appsign, b.Exepsnsign, b.Exepsndessign, b.Exepos, b.Crtdtesign, b.Agrdtesign, b.Appdtesign, b.Exedtesign, b.Rejectmsg, b.Сontrolflg, b.Сontrolmsg, ISNULL(z.Des, '') as Func, ISNULL(p.Adress, '') as Adress, ISNULL(p.Htel, '') as Tel, ISNULL(p.Email, '') as Email, ISNULL(p.Passer, '') as Passer, ISNULL(p.Pasnum, '') as Pasnum, ISNULL(p.Distrdte, '') as Distrdte, ISNULL(p.Distrby, '') as Distrby, ISNULL(p.Des, '') as FIO " +
            "from Tcpoahdr b " +
            "left join Tcpatmst a on b.Tcpat = a.Tcpat " +
            "left join Psnbrn c on b.Psn=c.Psn and b.Tabnum=c.Tabnum and c.Rcdsts < 9 " +
            "left join Clnmst d on c.Cln=d.Cln and c.Ptnbrn=d.Brn " +
            "left join Psnmst p on p.Psn=b.Psn " +
            "left join Ptnmst x on x.Ptn=b.Ptn " +
            "left join Brnmst y on y.Brn=b.Brn " +
            "left join Pstmst z on z.Pst=p.Pst " +
            "WHERE b.Tcpoa = #{Tcpoa}")
    MainController.AddDoc getDocElem(@Param("Tcpoa") int Tcpoa);


    @Select("select b.Tcpoa, b.Actdte, ISNULL(d.Des, '') as Dep, a.Des as Template, b.Crtpsndessign, b.Crtdtesign, b.Agrpsndessign, b.Agrdtesign, b.Apppsndessign, b.Appdtesign, b.Exepsndessign, b.Exedtesign " +
            "from Tcpoahdr b " +
            " left join Tcpatmst a on b.Tcpat = a.Tcpat " +
            " left join Psnbrn c on b.Psn=c.Psn and b.Tabnum=c.Tabnum and c.Rcdsts < 9 " +
            " left join Clnmst d on c.Cln=d.Cln and c.Ptnbrn=d.Brn " +
            "WHERE b.Actdte > #{startTime} AND b.Actdte < #{endTime} " +
            "ORDER by ${order} " +
            "offset ${i1} rows " +
            "fetch next ${i2} rows only")
    List<DocElem> getDocsLst(@Param("order") String order, @Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("i1") int i1, @Param("i2") int i2);

    @Select("SELECT COUNT(Tcpoa) FROM Tcpoahdr WHERE Actdte > #{startTime} AND Actdte < #{endTime}")
    int getDocsCnt(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    @Select("SELECT Docx FROM Tcpatmst WHERE Tcpat = #{Tcpat}")
    byte[] getFile(@Param("Tcpat") int Tcpat);

    @Select("SELECT Des as val, Brn as key1, Cln as key2 FROM Clnmst")
    List<MainController.Dep> getDepLst();

    @Select("SELECT Des, Tcpat FROM Tcpatmst")
    List<Templeate> getTmpLst();

    @Select("SELECT Des, Tcpat FROM Tcpatmst WHERE Crtdoctyp=4")
    List<Templeate> getTmpLstWO();

    @Select("SELECT DISTINCT Tcpoa as key1, Psndes as value FROM Tcpoahdr")
    List<Names> getFIOLst();

    @Select("SELECT MAX(Tcpoa) FROM Tcpoahdr")
    int getMaxTcpoa();

    @Insert("INSERT INTO Tcpoahdr " +
            "(Tcpoa, Tcpat, Actdte, Entdte, Brn, Psn, Entby, Tabnum, Psndes, Crtpsnsign, Crtpsndessign, Agrpsnsign, Agrpsndessign, Apppsnsign, Apppsndessign, Exepsnsign, Exepsndessign) " +
            "VALUES (#{tcpoa}, #{tmp}, #{actdte}, #{entdte}, 1, #{psn}, #{entby}, #{tabnum}, #{psndes}, #{psnCrt},  #{psndesCrt}, #{psnAgr}, #{psndesAgr}, #{psnApp}, #{psndesApp}, #{psnExe}, #{psndesExe})")
    boolean insertTcpoahdr(int tcpoa, int tmp, Date actdte, Date entdte, String psn, String entby, String tabnum, String psndes, String psnCrt, String psndesCrt, String psnAgr, String psndesAgr, String psnApp, String psndesApp, String psnExe, String psndesExe);

    @Insert("INSERT INTO Tcpoahdr " +
            "(Tcpoa, Tcpat, Actdte, Entdte, Brn, Psn, Entby, Tabnum, Psndes, Strdte, Stpdte, Qnt) " +
            "VALUES (#{tcpoa}, #{tmp}, #{actdte}, #{entdte}, 1, #{psn}, #{entby}, #{tabnum}, #{psndes}, #{Strdte}, #{Stpdte}, #{qnt})")
    boolean insertTcpoahdr_(int tcpoa, int tmp, Date actdte, Date entdte, String psn, String entby, String tabnum, String psndes, Date Strdte, Date Stpdte, int qnt);

    @Select("SELECT * FROM Tcpoahdr WHERE Tcpoa = #{tcpoa}")
    Tcpoahdr getTcpoahdrElem(int tcpoa);

    @Select("SELECT tcpat, des, header, text, pctstamp, wordstamp, linkstamp, entby, lstchgby, entdte, lstchgdte, rcdsts, crtdoctyp, agrdoctyp, aprdoctyp, exedoctyp, lifetime, savetime, comment, newtmp, multisign FROM Tcpatmst WHERE Tcpat = #{tcpat}")
    TcpatmstNF getTcpatmstElem(int tcpat);

    @Select("SELECT Des from Psnmst WHERE Psn = #{psn}")
    String getPsndes(String psn);

    @Select("SELECT *  from Psnmst WHERE Psn = #{psn}")
    Psnmst getPsnmst(String psn);

    @Select("SELECT *  from Psnbrn WHERE Psn = #{psn}")
    Psnbrn getPsnbrn(String psn);

    @Select("SELECT Tabnum from Psnbrn WHERE Psn = ${psn}")
    String getTab(String psn);

    @Select("SELECT DISTINCT a.Psn from Psnmst a, Psnbrn b, Clnmst c WHERE a.Psn=b.Psn AND b.Cln = c.Cln AND c.Brn = #{brn} AND c.Cln = #{cln}")
    List<String> getPsnLst(int brn, int cln);

    @Select("SELECT DISTINCT a.Psn from Psnmst a, Psnbrn b, Clnmst c WHERE a.Psn=b.Psn AND b.Cln = c.Cln")
    List<String> getPsnLstNPR();

    @Update("UPDATE Tcpoahdr SET Crtpsnsign = #{psn}, Crtpsndessign = #{psndes}, Crtpos = #{passhash}, Crtdtesign = #{dtesign} WHERE Tcpoa = #{tcpoa}")
    int addSignCrt(String psn, String psndes, String passhash, Date dtesign, int tcpoa);

    @Update("UPDATE Tcpoahdr SET Agrpsnsign = #{psn}, Agrpsndessign = #{psndes}, Agrpos = #{passhash}, Agrdtesign = #{dtesign} WHERE Tcpoa = #{tcpoa}")
    int addSignAgr(String psn, String psndes, String passhash, Date dtesign, int tcpoa);

    @Update("UPDATE Tcpoahdr SET Apppsnsign = #{psn}, Apppsndessign = #{psndes}, Apppos = #{passhash}, Appdtesign = #{dtesign} WHERE Tcpoa = #{tcpoa}")
    int addSignApp(String psn, String psndes, String passhash, Date dtesign, int tcpoa);

    @Update("UPDATE Tcpoahdr SET Exepsnsign = #{psn}, Exepsndessign = #{psndes}, Exepos = #{passhash}, Exedtesign = #{dtesign} WHERE Tcpoa = #{tcpoa}")
    int addSignExe(String psn, String psndes, String passhash, Date dtesign, int tcpoa);

    @Update("UPDATE Tcpoahdr SET Crtpsnsign = #{psnCrt}, Crtpsndessign = #{psndesCrt}, Agrpsnsign = #{psnAgr}, Agrpsndessign = #{psndesAgr}, Apppsnsign = #{psnApp}, Apppsndessign = #{psndesApp}, Exepsnsign = #{psnExe}, Exepsndessign = #{psndesExe}, Сontrolflg = #{controlflg}, Сontrolmsg = #{controlmsg} WHERE Tcpoa = #{tcpoa}")
    int updateTcpoahdr(String psnCrt, String psndesCrt, String psnAgr, String psndesAgr, String psnApp, String psndesApp, String psnExe, String psndesExe, int controlflg, String controlmsg, int tcpoa);

    @Update("UPDATE Tcpoahdr SET Agrpsnsign = '<REJECT>', Apppsnsign = '<REJECT>', Exepsnsign = '<REJECT>', Rejectmsg = #{msg} WHERE Tcpoa = #{tcpoa}")
    int updateTcpoahdrRejectAgr(String msg, int tcpoa);

    @Update("UPDATE Tcpoahdr SET Apppsnsign = '<REJECT>', Exepsnsign = '<REJECT>', Rejectmsg = #{msg} WHERE Tcpoa = #{tcpoa}")
    int updateTcpoahdrRejectApp(String msg, int tcpoa);

    @Update("UPDATE Tcpoahdr SET Exepsnsign = '<REJECT>', Rejectmsg = #{msg} WHERE Tcpoa = #{tcpoa}")
    int updateTcpoahdrRejectExe(String msg, int tcpoa);

    @Update("UPDATE Tcpoahdr SET Code = #{code} WHERE Tcpoa = #{tcpoa}")
    int updateTcpoahdrCode(String code, int tcpoa);


    @Select("SELECT c.Des FROM Psnbrn p, Cscmst c WHERE p.Csc=c.Csc AND p.Psn=#{psn}")
    String getCscdes(String psn);

    @Select("SELECT c.Psn FROM Psnbrn p, Cscmst c WHERE p.Csc=c.Csc AND p.Psn=#{psn}")
    String getCscpsn(String psn);
}
