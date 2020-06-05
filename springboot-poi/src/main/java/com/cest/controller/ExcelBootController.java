package com.cest.controller;


import com.cest.mapper.ResultMapper;
import com.cest.pojo.ResultEntity;
import com.cest.pojo.UserEntity;
import com.cest.util.DateUtil;
import com.cest.vo.ParamEntity;
import com.excel.poi.ExcelBoot;
import com.excel.poi.entity.ErrorEntity;
import com.excel.poi.function.ExportFunction;
import com.excel.poi.function.ImportFunction;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static com.excel.poi.ExcelBoot.ExportBuilder;


@Slf4j
@RestController
@RequestMapping("import")
public class ExcelBootController {

    @Autowired
    private ResultMapper resultMapper;

    /**
     * 导入Demo
     * localhost:8089/import/importExcel
     * UserEntity是标注注解的类, Excel的导入映射类, onProcess的userEntity参数则是Excel每行数据的映射实体
     * 需要使用本组件的开发者自己定义
     *
     * ErrorEntity是封装了每行Excel数据常规校验后的错误信息实体, 封装了sheet号、行号、列号、单元格值、所属列名、错误信息
     *
     * onProcess方法是用户自己实现, 当经过正则或者判空常规校验成功后执行的方法, 参数是每行数据映射的实体
     * onError方法是用户自己实现, 当经过正则或者判空常规校验失败后执行的方法
     */
    @GetMapping("/importExcel")
    public void importExcel() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File("D:\\tmp\\导入1.xlsx"));

        if(fileInputStream != null) {
            ExcelBoot.ImportBuilder(fileInputStream,  UserEntity.class)
                    .importExcel(new ImportFunction<UserEntity>() {

                        /**
                         * @param sheetIndex 当前执行的Sheet的索引, 从1开始
                         * @param rowIndex 当前执行的行数, 从1开始
                         * @param userEntity Excel行数据的实体
                         */
                        @Override
                        public void onProcess(int sheetIndex,  int rowIndex,  UserEntity userEntity) {
                            //将读取到Excel中每一行的userEntity数据进行自定义处理
                            //如果该行数据发生问题,将不会走本方法,而会走onError方法
                            log.info("进入正确的处理方法");
                            System.out.println(userEntity.toString());
                        }

                        /**
                         * @param errorEntity 错误信息实体
                         */
                        @Override
                        public void onError(ErrorEntity errorEntity) {
                            //将每条数据非空和正则校验后的错误信息errorEntity进行自定义处理
                            log.info(errorEntity.getErrorMessage());
                        }
                    });
        }
    }


    /**
     * 浏览器导出Excel
     * localhost:8089/import/exportResponse
     * @param httpServletResponse
     */
    @GetMapping("/exportResponse")
    public void exportResponse(HttpServletResponse httpServletResponse) {
        ParamEntity queryQaram = new ParamEntity();
        String fileBashName = DateUtil.dateToString(new Date(),"YYYYMMDD") + new Random().nextInt(100000);

        ExportBuilder(httpServletResponse, fileBashName, UserEntity.class,10, 10, 80000, true)
//        ExportBuilder(httpServletResponse, fileBashName, UserEntity.class)
                .exportResponse(queryQaram, new ExportFunction<ParamEntity, ResultEntity>() {
                    /**
                     * @param queryQaram 查询条件对象
                     * @param pageNum    当前页数,从1开始
                     * @param pageSize   每页条数,默认3000
                     * @return
                     */
                    @Override
                    public List<ResultEntity> pageQuery(ParamEntity queryQaram, int pageNum, int pageSize) {
                        //1.将pageNum和pageSize传入使用本组件的开发者自己项目的分页逻辑中
                        log.info("pageNum:{}",pageNum);
                        log.info("pageSize:{}",pageSize);
                        PageHelper.startPage(pageNum, pageSize);
                        List<ResultEntity> resultEntities = resultMapper.selectAll();
                        //2.调用使用本组件的开发者自己项目的分页查询方法
//                        List<ResultEntity> result=dao().queryPage(queryQaram);

//                        return pageNum > 10 ? Collections.EMPTY_LIST : resultEntities;
                        return resultEntities;
                    }

                    /**
                     * 将查询出来的每条数据进行转换
                     *
                     * @param o
                     */
                    @Override
                    public UserEntity convert(ResultEntity o) {
                        //用于编写开发者自定义的转换逻辑
                        UserEntity userEntity = new UserEntity();
                        BeanUtils.copyProperties(o,userEntity);
                        return userEntity;
                    }
                });
    }


    /**
     * 浏览器多sheet导出Excel -- 报错
     * localhost:8089/import/exportMultiSheetResponse
     * @param httpServletResponse
     */
    @RequestMapping("/exportMultiSheetResponse")
    public void exportMultiSheetResponse(HttpServletResponse httpServletResponse) {
        String fileBashName = DateUtil.dateToString(new Date(),"YYYYMMDD") + new Random().nextInt(100000);

        ParamEntity queryQaram = new ParamEntity();
        ExcelBoot.ExportBuilder(httpServletResponse, fileBashName, UserEntity.class)
                .exportMultiSheetStream(queryQaram, new ExportFunction<ParamEntity, ResultEntity>() {
                    /**
                     * @param queryQaram 查询条件对象
                     * @param pageNum    当前页数,从1开始
                     * @param pageSize   每页条数,默认3000
                     * @return
                     */
                    @Override
                    public List<ResultEntity> pageQuery(ParamEntity queryQaram, int pageNum, int pageSize) {

                        //1.将pageNum和pageSize传入使用本组件的开发者自己项目的分页逻辑中
                        log.info("pageNum:{}",pageNum);
                        log.info("pageSize:{}",pageSize);
                        PageHelper.startPage(pageNum, pageSize);
                        List<ResultEntity> resultEntities = resultMapper.selectAll();
                        return resultEntities;
                    }

                    /**
                     * 将查询出来的每条数据进行转换
                     *
                     * @param o
                     */
                    @Override
                    public UserEntity convert(ResultEntity o) {
                        //用于编写开发者自定义的转换逻辑
                        UserEntity userEntity = new UserEntity();
                        BeanUtils.copyProperties(o,userEntity);
                        return userEntity;
                    }
                });
    }

    /**
     * localhost:8089/import/exportStream
     * 导出Excel到指定路径
     */
    @RequestMapping("/exportStream")
    public void exportStream() throws FileNotFoundException {
        ParamEntity queryQaram = new ParamEntity();
        ExcelBoot.ExportBuilder(new FileOutputStream(new File("D:\\tmp\\Excel文件.xlsx")), "Sheet名", UserEntity.class)
                .exportStream(queryQaram, new ExportFunction<ParamEntity, ResultEntity>() {
                    /**
                     * @param queryQaram 查询条件对象
                     * @param pageNum    当前页数,从1开始
                     * @param pageSize   每页条数,默认3000
                     * @return
                     */
                    @Override
                    public List<ResultEntity> pageQuery(ParamEntity queryQaram, int pageNum, int pageSize) {

                        //1.将pageNum和pageSize传入使用本组件的开发者自己项目的分页逻辑中
                        log.info("pageNum:{}",pageNum);
                        log.info("pageSize:{}",pageSize);
                        PageHelper.startPage(pageNum, pageSize);
                        List<ResultEntity> resultEntities = resultMapper.selectAll();
                        return resultEntities;
                    }

                    /**
                     * 将查询出来的每条数据进行转换
                     *
                     * @param o
                     */
                    @Override
                    public UserEntity convert(ResultEntity o) {
                        //用于编写开发者自定义的转换逻辑
                        UserEntity userEntity = new UserEntity();
                        BeanUtils.copyProperties(o,userEntity);
                        return userEntity;
                    }
                });
    }


    /**
     * 导出多sheet Excel到指定路径
     */
    @RequestMapping(value = "exportMultiSheetStream")
    public void exportMultiSheetStream() throws FileNotFoundException {
        ParamEntity queryQaram = new ParamEntity();
        ExcelBoot.ExportBuilder(new FileOutputStream(new File("D:\\tmp\\Excel文件.xlsx")), "Sheet名", UserEntity.class)
                .exportMultiSheetStream(queryQaram, new ExportFunction<ParamEntity, ResultEntity>() {
                    /**
                     * @param queryQaram 查询条件对象
                     * @param pageNum    当前页数,从1开始
                     * @param pageSize   每页条数,默认3000
                     * @return
                     */
                    @Override
                    public List<ResultEntity> pageQuery(ParamEntity queryQaram, int pageNum, int pageSize) {

                        //1.将pageNum和pageSize传入使用本组件的开发者自己项目的分页逻辑中
                        log.info("pageNum:{}",pageNum);
                        log.info("pageSize:{}",pageSize);
                        PageHelper.startPage(pageNum, pageSize);
                        List<ResultEntity> resultEntities = resultMapper.selectAll();
                        return resultEntities;
                    }

                    /**
                     * 将查询出来的每条数据进行转换
                     *
                     * @param o
                     */
                    @Override
                    public UserEntity convert(ResultEntity o) {
                        //用于编写开发者自定义的转换逻辑
                        return new UserEntity();
                    }
                });
    }




    /**
     * localhost:8089/import/exportTemplate
     * 导出Excel模板
     */
    @RequestMapping("/exportTemplate")
    public void exportTemplate(HttpServletResponse httpServletResponse) {
        String filename = "模板名称--xxxxx";
        ExcelBoot.ExportBuilder(httpServletResponse, filename, UserEntity.class).exportTemplate();
    }
}
