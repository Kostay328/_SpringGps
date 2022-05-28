package ru.ining.gps.mssqlmappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import ru.ining.gps.controllers.MainController;
import ru.ining.gps.controllers.MainController.Names;
import ru.ining.gps.controllers.MainController.Templeate;
import ru.ining.gps.entity.CarMillage;
import ru.ining.gps.entity.DocElem;

import java.util.Date;
import java.util.List;

@Mapper
public interface UnionMapper {
    @Select("select b.Tcpoa, b.Actdte, ISNULL(d.Des, '') as Dep, a.Des as Template, b.Crtpsnsign, b.Crtdtesign, b.Agrpsnsign, b.Agrdtesign, b.Apppsnsign, b.Appdtesign, b.Exepsnsign, b.Exedtesign " +
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

    @Select("SELECT DISTINCT Tcpoa as key1, Psndes as value FROM Tcpoahdr")
    List<Names> getFIOLst();
}
