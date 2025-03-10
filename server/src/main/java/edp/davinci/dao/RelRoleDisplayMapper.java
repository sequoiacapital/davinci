/*
 * <<
 *  Davinci
 *  ==
 *  Copyright (C) 2016 - 2019 EDP
 *  ==
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *  >>
 *
 */

package edp.davinci.dao;

import edp.davinci.model.RelRoleDisplay;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RelRoleDisplayMapper {
    int insert(RelRoleDisplay record);

    void insertBatch(List<RelRoleDisplay> list);

    @Delete({
            "delete from rel_role_display where display_id = #{id}"
    })
    int deleteByDisplayId(Long id);

    @Select({
            "select rrd.display_id",
            "from rel_role_display rrd",
            "       inner join rel_role_user rru on rru.role_id = rrd.role_id",
            "       inner join display d on d.id = rrd.display_id",
            "where rru.user_id = #{userId} and rrd.visible = 0 and d.project_id = #{projectId}",
    })
    List<Long> getDisableDisplayByUser(@Param("userId") Long userId, @Param("projectId") Long projectId);

    @Select({
            "select count(1)",
            "from rel_role_display rrd inner join rel_role_user rru on rru.role_id = rrd.role_id",
            "where rru.user_id = #{userId} and rrd.display_id = #{displayId} and rrd.visible = 0"
    })
    boolean isDisable(@Param("displayId") Long displayId, @Param("userId") Long userId);

    @Select({
            "select role_id from rel_role_display where display_id = #{display_id} and visible = 0"
    })
    List<Long> getById(Long displayId);

    @Select({
            "select rrd.display_id",
            "from rel_role_display rrd",
            "inner join display d on d.id = rrd.display_id",
            "where rrd.role_id = #{id} and rrd.visible = 0 and d.project_id = #{projectId}"
    })
    List<Long> getExecludeDisplays(@Param("id") Long id, @Param("projectId") Long projectId);

    @Delete({"delete from rel_role_display where display_id = #{displayId} and role_id = #{roleId}"})
    int delete(@Param("displayId") Long displayId, @Param("roleId") Long roleId);

    @Delete({"delete from rel_role_display where role_id = #{roleId}"})
    int  deleteByRoleId(Long roleId);
}