package thunderCore.managers.reportManager

import com.google.gson.Gson
import org.bukkit.entity.Player
import thunderCore.ThunderCore
import thunderCore.managers.ThunderManager
import thunderCore.managers.fileManager.FileManager
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class ReportManager : ThunderManager {
    companion object {
        lateinit var get: ReportManager
    }
    init { get = this}
    var reports: ArrayList<ReportRecord> = ArrayList()
    private val gson: Gson = Gson()

    fun createReport(reporter: Player, reported: Player, reason: String, id: UUID) {
        reports.add(ReportRecord(reporter, reported, reason, id))
    }

    fun removeReport(report: ReportRecord) {
        reports.remove(report)
    }

    fun getReportByReported(player: Player): ReportRecord? {
        if (reports.isNotEmpty()) {
            for (reportRecord in reports) {
                if (reportRecord.reported == player) {
                    return reportRecord
                }
            }
        }
        return null
    }

    fun getReportByReporter(player: Player): ReportRecord? {
        if (reports.isNotEmpty()) {
            for (reportRecord in reports) {
                if (reportRecord.reporter == player) {
                    return reportRecord
                }
            }
        }
        return null
    }

    fun getReportByID(id: UUID): ReportRecord? {
        if (reports.isNotEmpty()) {
            for (reportRecord in reports) {
                if (reportRecord.id == id) {
                    return reportRecord
                }
            }
        }
        return null
    }

    override fun load() {
        try {
            val folder = File("ThunderCore/Reports/")
            val listOfFiles: Array<File> = folder.listFiles()!!
            for (file in Objects.requireNonNull(listOfFiles)) {
                val fileContent: String? = FileManager.get.readFile(file)
                reports.add(gson.fromJson(fileContent, ReportRecord::class.java))
            }
            reports =
                gson.fromJson(Objects.requireNonNull(FileManager.get.readFile(File("Reports.json"))), reports.javaClass)
        } catch (e: NullPointerException) {
            ThunderCore.get.yellowMsg("There are no report files!")
        }
        ThunderCore.get.greenMsg("Reports loaded!")
    }

    override fun save() {
        for (report in reports) {
            val id = report.id.toString()
            FileManager.get.writeFile(File("ThunderCore/Reports/Report${id}.json"), gson.toJson(report))
        }
        ThunderCore.get.greenMsg("Saved Reports!")
    }
}
