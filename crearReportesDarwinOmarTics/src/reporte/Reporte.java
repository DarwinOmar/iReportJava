package reporte;

import dao.DAOPostgres;
import dao.Parametro;
import java.awt.HeadlessException;
import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class Reporte extends DAOPostgres {

    /**
     * Este reporte es la primera version que realice, pero no contenia
     * parametros(a la bd)
     *
     * @param nombreArchivo nombre del archivo jasper
     * @throws java.lang.Exception
     */
    public void generarReporte(String nombreArchivo) throws Exception {
        String ubicacionReporte;
        try {
            ubicacionReporte = System.getProperty("user.dir") + "/src/reportes/" + nombreArchivo;
            JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(ubicacionReporte);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, this.conexionReporte());
            JasperViewer view = new JasperViewer(jasperPrint, false);
            view.setVisible(true);
        } catch (JRException e) {
            throw e;
        }
    }

    /**
     * v2 Este metodo es la segunda version del metodo generar reporte
     *
     * @param nombreArchivo hace referencia a archivos jasper
     * @param listaParametro es la lista de parametro que se van a ingresar, el
     * pimer lavlor es el nombre del archivo y el siguente valor es el valor que
     * se le enviara
     * @param nombreArchivoGuardar
     * @param operacion
     * @throws java.lang.Exception
     */
    public void generarReporteParametro(String nombreArchivo, List<Parametro> listaParametro, String nombreArchivoGuardar, int operacion) throws Exception {

        String ubicacionReporte;
        File fichero = null;
        Map param;
        param = new HashMap();
        try {
            ubicacionReporte = System.getProperty("user.dir") + "\\src\\reporte\\" + nombreArchivo;
            fichero = new File(ubicacionReporte);
            if (fichero.exists() == true) {
                JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(ubicacionReporte);
                if (listaParametro != null) {
                    for (Parametro par : listaParametro) {
                        param.put(par.getNombre(), par.getValor());
                    }
                } else {
                    param = null;
                }
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, param, this.conexionReporte());
                JasperViewer view = new JasperViewer(jasperPrint, false);
                view.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "NO EXISTE EL ARCHIVO");
            }
        } catch (JRException | HeadlessException e) {
            throw e;
        }
    }

    public void generarReportev1(String nombreArchivoJasper) throws ClassNotFoundException, SQLException, JRException {
        JasperReport jasperReport;
        JasperPrint jasperPrint;
        try {
            URL in = this.getClass().getResource(nombreArchivoJasper);             //se carga el reporte
            System.out.println(in);// Me retorna la ruta del archivo
            jasperReport = (JasperReport) JRLoader.loadObject(in);
            jasperPrint = JasperFillManager.fillReport(jasperReport, null, this.conexionReporte());
            JasperViewer view = new JasperViewer(jasperPrint, false);
            view.setVisible(true);
        } catch (JRException ex) {
            throw ex;
        }
    }

    public void generarReportev2(String nombreArchivoJasper, List<Parametro> listaParametro) throws JRException, SQLException, ClassNotFoundException {
        Map param = new HashMap();
        try {
            URL in = this.getClass().getResource(nombreArchivoJasper);
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(in);
            if (listaParametro != null) {
                for (Parametro par : listaParametro) {
                    param.put(par.getNombre(), par.getValor());
                }
            } else {
                param = null;
            }
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, param, this.conexionReporte());
            JasperViewer view = new JasperViewer(jasperPrint, false);
            view.setVisible(true);
        } catch (JRException ex) {
            throw ex;
        }
    }

    public void generarReporte(String nombreArchivoJasper, List<Parametro> listaParametro, String nombreArchivoPDF) throws JRException, SQLException, ClassNotFoundException {
        JasperReport jasperReport;
        JasperPrint jasperPrint;
        Map param;
        param = new HashMap();
        try {
            //se carga el reporte
            URL in = this.getClass().getResource(nombreArchivoJasper);
//            System.out.println(in); Me retorna la ruta del archivo
            jasperReport = (JasperReport) JRLoader.loadObject(in);
            //se procesa el archivo jasper
            if (listaParametro != null) {
                for (Parametro par : listaParametro) {
                    param.put(par.getNombre(), par.getValor());
                }
            } else {
                param = null;
            }
            jasperPrint = JasperFillManager.fillReport(jasperReport, param, this.conexionReporte());
            JasperViewer view = new JasperViewer(jasperPrint, false);
            view.setVisible(true);
            //se crea el archivo PDF
            JasperExportManager.exportReportToPdfFile(jasperPrint, "C:\\xampp\\htdocs\\Archivos\\" + nombreArchivoPDF + ".pdf");
        } catch (JRException ex) {
            throw ex;
        }
    }

}
