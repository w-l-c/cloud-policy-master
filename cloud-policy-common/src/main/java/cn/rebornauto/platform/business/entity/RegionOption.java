package cn.rebornauto.platform.business.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RegionOption {
    private String value;
    private String label;
    private List<RegionOption> children = new ArrayList<>();
}
