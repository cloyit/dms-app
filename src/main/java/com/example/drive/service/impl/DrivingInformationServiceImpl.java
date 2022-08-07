package com.example.drive.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.drive.entity.Dpicture;
import com.example.drive.entity.DrivingInformation;
import com.example.drive.mapper.DpictureMapper;
import com.example.drive.mapper.DrivingInformationMapper;
import com.example.drive.service.IDrivingInformationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.drive.utills.FastDFSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhulu
 * @since 2021-12-22
 */
@Service
public class DrivingInformationServiceImpl extends ServiceImpl<DrivingInformationMapper, DrivingInformation> implements IDrivingInformationService {

    @Autowired
    DrivingInformationMapper drivingInformationMapper;

    @Autowired
    private DpictureMapper dpictureMapper;

    /**
     * 根据日期获取驾驶记录，精确到日
     */
    @Override
    public List<List<DrivingInformation>> getDrivingInformationByDay(Long uid, LocalDateTime beginTime) {
        LocalDateTime endTime = LocalDateTime.of(beginTime.getYear(),
                                                 beginTime.getMonth(),
                                                 beginTime.getDayOfMonth() + 1,
                                                 0, 0);

        QueryWrapper<DrivingInformation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", uid);
        queryWrapper.between("begin", beginTime, endTime);
        List<DrivingInformation> DriveList = drivingInformationMapper.selectList(queryWrapper);

        if (DriveList != null && DriveList.size() != 0) {

            List<List<DrivingInformation>> result = new ArrayList<>();
            //拆分数据,设置标识，循环遍历
            int flag = 0;
            for (DrivingInformation drivingInformation : DriveList) {
                if (flag == drivingInformation.getBegin().getDayOfYear()) {
                    result.get(result.size() - 1).add(drivingInformation);
                } else {
                    //新建并且更新flag
                    flag = drivingInformation.getBegin().getDayOfYear();
                    List<DrivingInformation> r = new ArrayList<>();
                    r.add(drivingInformation);
                    result.add(r);
                }
            }
            return result;
        }
        return null;

    }

    @Override
    public List<List<DrivingInformation>> getDrivingInformationByDay(Long uid, LocalDateTime beginTime, LocalDateTime endTime) {
        QueryWrapper<DrivingInformation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", uid);
        queryWrapper.between("begin", beginTime, endTime);
        List<DrivingInformation> DriveList = drivingInformationMapper.selectList(queryWrapper);

        if (DriveList != null && DriveList.size() != 0) {
            List<List<DrivingInformation>> result = new ArrayList<>();
            //拆分数据,设置标识，循环遍历
            int flag = 0;
            for (DrivingInformation drivingInformation : DriveList) {
                if (flag == drivingInformation.getBegin().getDayOfYear()) {
                    result.get(result.size() - 1).add(drivingInformation);
                } else {
                    //新建且更新flag
                    flag = drivingInformation.getBegin().getDayOfYear();
                    List<DrivingInformation> r = new ArrayList<>();
                    r.add(drivingInformation);
                    result.add(r);
                }
            }
            return result;
        }
        return null;
    }

    @Override
    public Dpicture uploadPicture(MultipartFile file, Integer did) {
        Dpicture dpicture = new Dpicture();
        dpicture.setDid(did);
        String[] result = null;
        int num = dpictureMapper.selectCount(null);

        try {
            result = FastDFSUtil.upload(file.getBytes(), "" + (num + 1) + ".jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        dpicture.setUrl("http://47.102.99.215/" + result[0] + "/" + result[1]);
        dpictureMapper.insert(dpicture);
        return dpicture;
    }
}
