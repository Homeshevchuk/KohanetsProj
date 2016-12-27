package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 27.12.2016.
 */
@RestController
@Repository
public class Controller  {
    @Autowired
    JdbcTemplate template;
    @RequestMapping("/req1")
    public List<Person> request1(@RequestParam("type")String type) throws SQLException {
       return select1(type);
    }
    @RequestMapping("/req2")
    public List<Person> request2(@RequestParam("speciallity")String speciallity) throws SQLException {
        return select2(speciallity);
    }
    @RequestMapping("/req3")
    public List<Person> request3(@RequestParam("predmet")String predmet,@RequestParam("mark")String mark) throws SQLException {
        return select3(predmet,mark);
    }
    private List<Person> select1(String type) throws SQLException {
        Statement statement = template.getDataSource().getConnection().createStatement();
        List<Person> list = new ArrayList<>();

        String statementString = "SELECT * FROM person\n" +
                "JOIN violation on person.Violation_ID = violation.Violation_ID\n" +
                "JOIN sviolation_kind on violation.Violation_kind_ID = sviolation_kind.Violation_kind_ID\n" +
                "WHERE sviolation_kind.Violation_kind_name='%s';";
        String select = String.format(statementString, type);

        ResultSet set = statement.executeQuery(select);
        while (set.next()) {
            Person person = new Person();
            person.setSurname(set.getString("Surname"));
            person.setName(set.getString("Name"));
            person.setAddress(set.getString("Address"));
            person.setSex(set.getString("Sex"));
            person.setPatronymic(set.getString("Patronymic"));
            person.setTelephon(set.getString("Telephon"));
            person.setBirthDate(set.getDate("Birth_date"));
            person.setBirthPlace(set.getString("Birth_place"));
            list.add(person);
        }
        return list;
    }
    private List<Person> select2(String speciallity) throws SQLException {
        Statement statement = template.getDataSource().getConnection().createStatement();
        List<Person> list = new ArrayList<>();

        String statementString = "Select * from groups\n" +
                "        join speciality on groups.Speciality_ID=speciality.Speciality_ID\n" +
                "        join student_group on student_group.Group_ID = groups.Group_ID\n" +
                "        join person on person.Student_ID = student_group.Student_ID\n" +
                "        where speciality.Speciality_name='%s'";
        String select = String.format(statementString, speciallity);

        ResultSet set = statement.executeQuery(select);
        while (set.next()) {
            Person person = new Person();
            person.setSurname(set.getString("Surname"));
            person.setName(set.getString("Name"));
            person.setAddress(set.getString("Address"));
            person.setSex(set.getString("Sex"));
            person.setPatronymic(set.getString("Patronymic"));
            person.setTelephon(set.getString("Telephon"));
            person.setBirthDate(set.getDate("Birth_date"));
            person.setBirthPlace(set.getString("Birth_place"));
            list.add(person);
        }
        return list;
    }
    private List<Person> select3(String predmet, String mark) throws SQLException {
        Statement statement = template.getDataSource().getConnection().createStatement();
        List<Person> list = new ArrayList<>();

        String statementString = "Select * from teach_plan\n" +
                "        join subject on subject.Subject_ID = teach_plan.Subject_ID\n" +
                "        Join student_marks on student_marks.Teach_plan_ID = teach_plan.Teach_plan_ID\n" +
                "        join smark on student_marks.Mark_ID = smark.Mark_ID\n" +
                "        join person on student_marks.Student_ID = person.Person_ID\n" +
                "        where subject.Subjact_name='%s' and smark.Mark_name = '%s'\n" +
                "        group by person.Student_ID";
        String select = String.format(statementString, predmet,mark);

        ResultSet set = statement.executeQuery(select);
        while (set.next()) {
            Person person = new Person();
            person.setSurname(set.getString("Surname"));
            person.setName(set.getString("Name"));
            person.setAddress(set.getString("Address"));
            person.setSex(set.getString("Sex"));
            person.setPatronymic(set.getString("Patronymic"));
            person.setTelephon(set.getString("Telephon"));
            person.setBirthDate(set.getDate("Birth_date"));
            person.setBirthPlace(set.getString("Birth_place"));
            list.add(person);
        }
        return list;
    }
}
