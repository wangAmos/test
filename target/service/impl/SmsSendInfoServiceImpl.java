package com.my.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.my.mapper.SmsSendInfoMapper;

import java.util.List;

@Service
public class SmsSendInfoServiceImpl implements SmsSendInfoService{

	@Autowired
    private SmsSendInfoMapper<SmsSendInfo> smsSendInfoMapper;

	@Override
	public boolean insert(SmsSendInfo smsSendInfo) {
		try {
			smsSendInfoMapper.insert(smsSendInfo);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(SmsSendInfo smsSendInfo) {
		try {
			smsSendInfoMapper.update(smsSendInfo);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void updateBySelective(SmsSendInfo smsSendInfo) {
		try {
			smsSendInfoMapper.updateBySelective(smsSendInfo);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void deleteById(Object id) {
		try {
			smsSendInfoMapper.deleteById(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public SmsSendInfo getById(Object id){
		return smsSendInfoMapper.getById(id);
	}

	@Override
	public List<SmsSendInfo> findBySelective(SmsSendInfo smsSendInfo) {
		return smsSendInfoMapper.findBySelective(smsSendInfo);
	}

    @Override
    public List<SmsSendInfo> findPageList(SmsSendInfo smsSendInfo) {
        return smsSendInfoMapper.findPageList(smsSendInfo);
    }

    @Override
    public int findPageCount(SmsSendInfo smsSendInfo) {
        return smsSendInfoMapper.findPageCount(smsSendInfo);
    }

}
