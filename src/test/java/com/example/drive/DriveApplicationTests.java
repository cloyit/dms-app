package com.example.drive;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.drive.entity.*;
import com.example.drive.mapper.*;
import com.example.drive.service.IDrivingInformationService;
import com.example.drive.service.IUserService;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.*;
import java.lang.reflect.Array;
import java.net.Authenticator;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootTest
class DriveApplicationTests {
    @Autowired
    UserMapper userMapper;

    @Autowired
    PeachMapper peachMapper;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    TitleMapper titleMapper;
    @Autowired
    CarMapper carMapper;
    @Autowired
    DetailMapper detailMapper;
    @Autowired
    BrandMapper brandMapper;
    @Autowired
    UserHealthMapper userHealthMapper;
    @Autowired
    IDrivingInformationService iDrivingInformationService;
    @Autowired
    IUserService iUserService;
    @Autowired
    DrivingInformationMapper drivingInformationMapper;
    @Autowired
    LeaveMapper leaveMapper;
    @Autowired
    RequiredMapper requiredMapper;
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    RelateMapper relateMapper;
    @Autowired
    SubjectMapper subjectMapper;
    @Test
    void contextLoads() {

   DrivingInformation drivingInformation = null;

   LocalDateTime beginTime;
        LocalDateTime endTime;
   Random e =new Random();
   for(int i=0;i<27;i++){
       drivingInformation = new DrivingInformation();
       beginTime =LocalDateTime.of(2022,1,i+1,12,24);
       endTime = LocalDateTime.of(2022,1,i+1,12+e.nextInt(4),20+e.nextInt(30));
       drivingInformation.setAttention(2+e.nextInt(3));
       drivingInformation.setUid(1473527123812581377L);
       drivingInformation.setBegin(beginTime);
       drivingInformation.setEnd(endTime);
       drivingInformation.setCarId(3);
       drivingInformation.setCloseEye(1+e.nextInt(3));
       drivingInformation.setEndLatitude(31+e.nextInt(2)+"");
       drivingInformation.setStartLatitude(31+e.nextInt(2)+"");
       drivingInformation.setStartLongitude(31+e.nextInt(2)+"");
       drivingInformation.setEndLongitude(31+e.nextInt(2)+"");
       drivingInformationMapper.insert(drivingInformation);
   }



    }

    @Test
    void test1() {


        List<DrivingInformation> drivingInformationList = drivingInformationMapper.selectList(null);
        for (DrivingInformation d : drivingInformationList) {
            Random r = new Random();
            d.setStartLatitude("114." + r.nextInt(40));
            d.setEndLatitude("115." + r.nextInt(40));
            d.setStartLongitude("30." + r.nextInt(40));
            d.setEndLongitude("31." + r.nextInt(40));
            QueryWrapper<DrivingInformation> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", d.getId());
            drivingInformationMapper.update(d, queryWrapper);
        }
    }
        @Test
        void test2(){
        userHealthMapper.delete(null);


        }

    @Test
    void test3() throws InterruptedException, Exception {

        //写训练数据
        //BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\86182\\Desktop\\CCIC_test\\wang.txt"));
        String line;
        BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\86182\\Desktop\\words\\train.txt"));


        String dirName="C:\\Users\\86182\\Desktop\\words\\train";
        File file = new File(dirName);
        if(file.isDirectory()) {
            System.out.println("正在读取"+dirName+"目录....");
            String[] list = file.list();
            for(int i=0;i<list.length;i++) {
                File file2 = new File(dirName+"\\"+list[i]);
                if(file2.isDirectory()) {
                    //获取所有文件，
                    File Pfiles = new File(dirName+"\\"+list[i]);
                    String[] plist = Pfiles.list();
                    //遍历，读取所有文件的名字并写入
                    for(String s:plist){
                        System.out.println(dirName+"\\"+list[i]+"\\"+s);
                        bw.write(dirName+"\\"+list[i]+"\\"+s+"\n");
                    }

                }else {
                    System.out.println("文件："+list[i]);
                }
            }
        }else {
            System.out.println(dirName+"不是一个目录。");
        }



    }



    @Test
    void test4() throws InterruptedException, Exception {
        //导入所有的必修课
        //测试插入
        ArrayList<String> d = new ArrayList<String>();
        d.add("通识必修");
        d.add("英语必修");
        d.add("专业必修");
        d.add("体育必修");
        d.add("实践课");
        Required required = new Required();
        required.setMajor("通信工程");
        required.setSubjectName("测试专业");
        try {
            //创建工作簿对象
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream("D:\\homework\\导入.xlsx"));
            //获取工作簿下sheet的个数
            int sheetNum = xssfWorkbook.getNumberOfSheets();
            System.out.println("该excel文件中总共有："+sheetNum+"个sheet");
            //遍历工作簿中的所有数据
            for(int i = 0;i<sheetNum;i++) {
                //读取第i个工作表
                System.out.println("读取第"+(i+1)+"个sheet");
                XSSFSheet sheet = xssfWorkbook.getSheetAt(i);
                //获取最后一行的num，即总行数。此处从0开始
                int maxRow = sheet.getLastRowNum();

                for (int row = 0; row <= maxRow; row++) {

                    //获取最后单元格num，即总单元格数 ***注意：此处从1开始计数***
                    int maxRol = sheet.getRow(row).getLastCellNum();
                    required.setSubjectName(sheet.getRow(row).getCell(2)+"");
                    required.setCredit(sheet.getRow(row).getCell(5)+"");
                    if(d.contains(sheet.getRow(row).getCell(3)+"")){
                        requiredMapper.insert(required);
                    }

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void test5() throws InterruptedException, Exception {


      File jidian = new File("D:\\homework\\jidian");
      File[] files = jidian.listFiles();
      QueryWrapper<Required> requiredQueryWrapper = new QueryWrapper<Required>();
      requiredQueryWrapper.eq("major","通信工程");
      List<Required>  requireds = requiredMapper.selectList(requiredQueryWrapper);
      ArrayList<String> requiredNames = new ArrayList<>();
      for(Required r :requireds){
          requiredNames.add(r.getSubjectName());
      }
        for (File file : files) {
            //读取所有文件
            try {
                //创建工作簿对象
                HSSFWorkbook hssfWorkbook = new HSSFWorkbook(new FileInputStream(file.getAbsolutePath()));
                //获取工作簿下sheet的个数
                int sheetNum = hssfWorkbook.getNumberOfSheets();
                System.out.println("该excel文件中总共有："+sheetNum+"个sheet");
                //遍历工作簿中的所有数据
                for(int i = 0;i<1;i++) {
                    //读取第i个工作表
                    System.out.println("读取第"+(i+1)+"个sheet");
                    HSSFSheet sheet = hssfWorkbook.getSheetAt(i);
                    //获取最后一行的num，即总行数。此处从0开始
                    int maxRow = sheet.getLastRowNum();
                    //row 0 为所有必修课
                    int rol0 = sheet.getRow(0).getLastCellNum();
                    for(int rol = 0;rol<=rol0;rol++){
                        //判断当前课程有没有被加进去
                        List<Subject> subjects =  subjectMapper.selectList(null);
                        ArrayList<String> SubjectNames = new ArrayList<String>();
                        for (Subject s : subjects){
                            SubjectNames.add(s.getName());
                        }
                        if(!SubjectNames.contains(sheet.getRow(0).getCell(rol)+"")){
                            //插入课程
                            Subject subject = new Subject();
                            subject.setName(sheet.getRow(0).getCell(rol)+"");
                            //根据subjectname查询credict
                            QueryWrapper<Required> subjectQueryWrap = new QueryWrapper();
                            subjectQueryWrap.eq("subject_name",sheet.getRow(0).getCell(rol)+"");
                            Required required = requiredMapper.selectOne(subjectQueryWrap);
                            if(required != null){
                                subject.setCredit(required.getCredit());
                                subjectMapper.insert(subject);
                            }
                        }
                    }
                    for (int row = 1; row <= maxRow; row++) {
                        //获取最后单元格num，即总单元格数 ***注意：此处从1开始计数***
                        int maxRol = sheet.getRow(row).getLastCellNum();
                        //对于每一行 都是一个同学，先获取同学的名字
                        List<Student> students =  studentMapper.selectList(null);
                        ArrayList<String> studentNames = new ArrayList<String>();
                        for (Student s : students){
                            studentNames.add(s.getName());
                        }
                        if(!studentNames.contains(sheet.getRow(row).getCell(1)+"")){
                            //如果同学未加入
                            Student student = new Student();
                            student.setXueHao(sheet.getRow(row).getCell(0)+"");
                            student.setName(sheet.getRow(row).getCell(1)+"");
                            student.setClassName(sheet.getRow(row).getCell(2)+"");
                            studentMapper.insert(student);
                        }
                        for(int rol = 3;rol<=maxRol;rol++){
                            //先判断是否是必修课
                            String className = sheet.getRow(0).getCell(rol)+"";
                            if(!requiredNames.contains(className)){
                                continue;
                            }
                            //判断成绩是否为空
                            String score = sheet.getRow(row).getCell(rol)+"";
                            if(score==null||score.equals("")){
                                continue;
                            }
                            Relate relate = new Relate();
                            //获取student id
                            String studentName = sheet.getRow(row).getCell(1)+"";
                            QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
                            queryWrapper.eq("name",studentName);
                            Student student = studentMapper.selectOne(queryWrapper);
                            relate.setStudentId(student.getId());
                            //获取课程id

                            QueryWrapper<Subject> SubjectQueryWrapper = new QueryWrapper<Subject>();
                            SubjectQueryWrapper.eq("name",className);
                            System.out.println("-------------------------------"+className);
                            Subject subject = subjectMapper.selectOne(SubjectQueryWrapper);
                            if(subject != null){
                                relate.setRequiredId(subject.getId());
                            }
                            //获取分数
                            relate.setScore(score);
                            relateMapper.insert(relate);
                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Test
    void test6()  {
       double sum = 0.0;
       double  xuefen = 0.0;
       List<Relate> r = null;
       QueryWrapper<Relate> queryWrapper = new QueryWrapper<>();
       queryWrapper.eq("student_id","285");
        r = relateMapper.selectList(queryWrapper);
        for(Relate re:r){
            //根据required id 查询学分
            QueryWrapper<Required> requiredQueryWrapper = new QueryWrapper<>();
            requiredQueryWrapper.eq("id",re.getRequiredId());
            Required required = requiredMapper.selectOne(requiredQueryWrapper);
            double score;
            if(required!=null){
                if(re.getScore().equals("优秀"))
                    re.setScore("95");
                if(re.getScore().equals("良好"))
                    re.setScore("85");
                if(re.getScore().equals("中等"))
                    re.setScore("75");
                sum += Double.parseDouble(required.getCredit()) * Double.parseDouble(re.getScore());
                xuefen += Double.parseDouble(required.getCredit());
            }

        }
        System.out.println(sum +"sum");
        System.out.println(xuefen+"xuefen");
        System.out.println(sum/xuefen+"绩点");


    }
}
