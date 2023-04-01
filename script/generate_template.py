#-*- coding:utf-8 -*-
from string import Template
import configparser
import json
from case_converter import *

config = configparser.ConfigParser()
# 读取配置文件
with open('config.properties', 'r', encoding='utf-8') as f:
    config.read_file(f)
# 读取 JSON 文件
with open('input.json', 'r') as f:
    data = json.load(f)

# 将参数项存储到字典中，后续替换进字符串中
params = dict(config.items(config.default_section))

data_name_camel = to_camel_case(params['data_name'])
data_name_snake = to_snake_case(params['data_name'])

default_params = {
    'project_name': data_name_camel,
    'project_name_pascal': capitalize_first_letter(data_name_camel),
    'url_name': data_name_camel,
    'class_name': capitalize_first_letter(data_name_camel),
    'object_name': uncapitalize_first_letter(data_name_camel),
    'table_name': data_name_snake,
    'actions' : ['select','insert','update','insertOrUpdate','delete','clear'],
    'actions_zh' : ['查询','插入','更新','插入或更新','删除','清空'],
    'actions_num': 6,
    'json_keys': list(data.keys()),
    'json_keys_num': len(data.keys()),
    'java_attrs': [to_camel_case(key) for key in data.keys()],
    'java_attr_types': ['String' for _ in data.keys()],
    'sql_fields': [to_snake_case(key) for key in data.keys()],
    'primary_key' : next(iter(data.keys())),
    'primary_attr' : to_camel_case(next(iter(data.keys()))),
    'primary_attr_type' : 'String',
    'primary_field': to_snake_case(next(iter(data.keys()))),
    'page_data_size': 12000
}

if config.has_option('DEFAULT', 'java_attrs'):
    params['java_attrs'] = [v.strip() for v in config.get('DEFAULT', 'java_attrs').split(',')]
if config.has_option('DEFAULT', 'sql_fields'):
    params['sql_fields'] = [v.strip() for v in config.get('DEFAULT', 'sql_fields').split(',')]


for key, default_value in default_params.items():
    params.setdefault(key, default_value)
    
locals().update(params)

# --- Common 层 ---

# 属性
text11 = f'''public class {class_name} {{
    // 属性'''

# 无参构造函数
text12 = f'''    // 无参构造函数
    public {class_name}() {{
    }}'''

# 成员列表构造函数
text13 = f'''    // 成员列表构造函数
    public {class_name}('''

text14 = f''' {{'''

# 复制构造函数
text15 = f'''    // 复制构造函数
    public {class_name}({class_name} {object_name}) {{'''

# Getter 方法
text16 = f'''    // Getter 方法'''

# Setter 方法
text17 = f'''    // Setter 方法'''

# 重写 toString 方法
text18 = f'''    // 重写 toString 方法
    @Override
    public String toString() {{
        return
            "{class_name}["'''

# --- Common 层 ---

# --- Controller 层 ---
text2 = '// 暂无'
# --- Controller 层 ---

# --- Service 层 ---
text3 = f'''
    @Autowired
    private {project_name_pascal}Mapper {project_name}Mapper;

    // 分页处理
    public <T> void pageHandle(List<T> list, int pageSize, Consumer<List<T>> handleFunction){{
        int count = 0;
        while (!list.isEmpty()) {{
            // 取出当前页数据
            List<T> subList = list.subList(0, Math.min(pageSize, list.size()));
            // 执行插入或更新操作
            handleFunction.accept(subList);
            // 统计插入或更新的记录数
            count += subList.size();
            // 从列表中移除已处理的数据
            list.subList(0, subList.size()).clear();
        }}
    }}

    // 分页处理
    public <T, R> List<R> pageHandle(List<T> list, int pageSize, Function<List<T>, List<R>> handleFunction) {{
        List<R> resultList = new ArrayList<>();
        int count = 0;
        while (!list.isEmpty()) {{
            // 取出当前页数据
            List<T> subList = list.subList(0, Math.min(pageSize, list.size()));
            // 执行查询操作
            List<R> subResultList = handleFunction.apply(subList);
            // 将结果添加到总结果列表中
            resultList.addAll(subResultList);
            // 统计查询的记录数
            count += subList.size();
            // 从列表中移除已处理的数据
            list.subList(0, subList.size()).clear();
        }}
        return resultList;
    }}
'''
# --- Service 层 ---

# --- Mapper 层 ---
text4 = ''
# --- Mapper 层 ---

# --- MyBatis 层 ---
text501 = '(' # 如 (`id`,`attr1`,`attr2`)
text502 = '(' # 如 (#{id},#{attr1},#{attr2}) 
text503 = '(' # 如 (#{item.id},222#{item.attr1},222#{item.attr2}) 
text504 = '' # 如 `id` = #{id}, `attr1` = #{attr1}, `attr2` = #{attr2}
text505 = '    <!-- WHEN ... THEN ... 语句相当于编程语言中的 switch 语句 -->' # 如 `id` = #{item.id}, `attr1` = #{item.attr1}, `attr2` = #{item.attr2}
text506 = '' # 如 `id` = VALUES(`id`), `attr1` = VALUES(`attr1`), `attr2` = VALUES(`attr2`)
# --- MyBatis 层 ---


# --- 数据库建表的 SQL 语句 ---
text61 = f'''DROP TABLE IF EXISTS `{table_name}`;
CREATE TABLE `{table_name}` ('''

text62 = ''
# --- 数据库建表的 SQL 语句 ---

for i in range(json_keys_num):
    key = params['json_keys'][i]
    java_attr = params['java_attrs'][i]
    java_attr_pascal = capitalize_first_letter(java_attr)
    java_attr_type = params['java_attr_types'][i]
    sql_field = params['sql_fields'][i]
    
    is_first = is_last = False
    if(i == 0): is_first = True
    if(i == json_keys_num - 1): is_last = True    
    
    text11 += f'''
    private {java_attr_type} {java_attr};'''

    text13 += f'''
        {java_attr_type} {java_attr}'''

    text14 += f'''
        this.{java_attr} = {java_attr};'''

    text15 += f'''
        this.{java_attr} = {object_name}.get{java_attr_pascal}();'''

    text16 += f'''
    public {java_attr_type} get{java_attr_pascal}() {{
        return {java_attr};
    }}'''
    

    text17 += f'''
    public void set{java_attr_pascal}(String {java_attr}) {{
        this.{java_attr} = {java_attr};
    }}
    '''

    text18 += f'''
            + "{java_attr}=" + {java_attr} + ", "'''

    text501 += f'`{sql_field}`'
    text502 += f'#{{{java_attr}}}'
    text503 += f'#{{item.{java_attr}}}'
    text504 += f'`{sql_field}` = #{{{java_attr}}}'

    text505 += f'''
            <trim prefix=" `{sql_field}` = CASE " suffix=" END, ">
                <foreach collection="list" item="item">
                    WHEN `{primary_attr}` = #{{item.{primary_attr}}} THEN #{{item.{java_attr}}}
                </foreach>
            </trim>'''

    text506 += f'`{sql_field}` = VALUES(`{sql_field}`)'
    text61 += f'''
    `{sql_field}` VARCHAR(255) DEFAULT NULL'''
    
    if(is_last):
        text13 += '''
    )'''
        text14 += '''
    }'''

        text15 += '''
    }'''
        text18 += '''
            + "]";
    }
}'''
        text501 += ')'
        text502 += ')'
        text503 += ')'   
        text61 += '''
) ENGINE=InnoDB DEFAULT CHARSET=utf8;'''
    else:
        text13 += ','
        text501 += ','
        text502 += ','
        text503 += ','
        text504 += ', '
        text506 += ', '
        text61 += ','
        
        
for i in range(actions_num):
    action = params['actions'][i]
    action_zh = params['actions_zh'][i]
    action_pascal = capitalize_first_letter(action)
    
    if(action in ['select']):
        text3 += f'''
    // 单项{action_zh}
    public {class_name} {action}{class_name}({primary_attr_type} {primary_attr}) {{
        return {project_name}Mapper.{action}{class_name}({primary_attr});
    }}

    // 依次{action_zh}
    public List<{class_name}> {action}{class_name}({primary_attr_type}... {primary_attr}Array) {{
        List<{class_name}> {object_name}List = new ArrayList<>();
        for({primary_attr_type} {primary_attr} : {primary_attr}Array){{
            {object_name}List.add({project_name}Mapper.{action}{class_name}({primary_attr}));
        }}
        return {object_name}List;
    }}

    // 批量{action_zh}
    public List<{class_name}> batch{action_pascal}{class_name}(List<{primary_attr_type}> {primary_attr}List) {{
        return {project_name}Mapper.batch{action_pascal}{class_name}({primary_attr}List);
    }}

    // 分页{action_zh}
    public List<{class_name}> page{action_pascal}{class_name}(List<{primary_attr_type}> {primary_attr}List) {{
        int pageDataSize = {page_data_size}; // 页面数据量大小，即每页记录数 × 字段数，可自行设置
        int totalFields = {class_name}.class.getDeclaredFields().length; // 总字段数，即数据表中的列数
        int pageSize = pageDataSize / totalFields; // 页面大小，即每页记录数

        return pageHandle({primary_attr}List, pageSize, {project_name}Mapper::batch{action_pascal}{class_name});
    }}
    '''
    
        text4 += f'''
        // 依次{action_zh}
        public {class_name} {action}{class_name}({primary_attr_type} {primary_attr});
        // 批量{action_zh}
        public List<{class_name}> batch{action_pascal}{class_name}(List<{primary_attr_type}> {primary_attr}List);'''

    
    
    elif(action in ['insert','update','insertOrUpdate']):
        text3 += f'''
    // 单项{action_zh}
    public void {action}{class_name}({class_name} {object_name}) {{
        {project_name}Mapper.{action}{class_name}({object_name});
        return ;
    }}

    // 依次{action_zh}
    public void {action}{class_name}({class_name}... {object_name}Array) {{
        for({class_name} {object_name} : {object_name}Array){{
            {project_name}Mapper.{action}{class_name}({object_name});
        }}
        return ;
    }}

    // 批量{action_zh}
    public void batch{action_pascal}{class_name}(List<{class_name}> {object_name}List) {{
        {project_name}Mapper.batch{action_pascal}{class_name}({object_name}List);
        return ;
    }}

    // 分页{action_zh}
    public void page{action_pascal}{class_name}(List<{class_name}> {object_name}List) {{
        int pageDataSize = {page_data_size}; // 页面数据量大小，即每页记录数 × 字段数，可自行设置
        int totalFields = {class_name}.class.getDeclaredFields().length; // 总字段数，即数据表中的列数
        int pageSize = pageDataSize / totalFields; // 页面大小，即每页记录数

        pageHandle({object_name}List, pageSize, {project_name}Mapper::batch{action_pascal}{class_name});
        return ;
    }}
    '''
        text4 += f'''
        // 依次{action_zh}
        public void {action}{class_name}({class_name} {object_name});
        // 批量{action_zh}
        public void batch{action_pascal}{class_name}(List<{class_name}> {object_name}List);'''
    
    elif(action in ['delete']):
        text3 += f'''
    // 单项{action_zh}
    public void {action}{class_name}({primary_attr_type} {primary_attr}) {{
        {project_name}Mapper.{action}{class_name}({primary_attr});
        return ;
    }}

    // 依次{action_zh}
    public void {action}{class_name}({primary_attr_type}... {primary_attr}Array) {{
        for({primary_attr_type} {primary_attr} : {primary_attr}Array){{
            {project_name}Mapper.{action}{class_name}({primary_attr});
        }}
        return ;
    }}

    // 批量{action_zh}
    public void batch{action_pascal}{class_name}(List<{primary_attr_type}> {primary_attr}List) {{
        {project_name}Mapper.batch{action_pascal}{class_name}({primary_attr}List);
        return ;
    }}

    // 分页{action_zh}
    public void page{action_pascal}{class_name}(List<{primary_attr_type}> {primary_attr}List) {{
        int pageDataSize = {page_data_size}; // 页面数据量大小，即每页记录数 × 字段数，可自行设置
        int totalFields = {class_name}.class.getDeclaredFields().length; // 总字段数，即数据表中的列数
        int pageSize = pageDataSize / totalFields; // 页面大小，即每页记录数

        pageHandle({primary_attr}List, pageSize, {project_name}Mapper::batch{action_pascal}{class_name});
        return ;
    }}
    '''
        text4 += f'''
        // 依次{action_zh}
        public void {action}{class_name}({primary_attr_type} {primary_attr});
        // 批量{action_zh}
        public void batch{action_pascal}{class_name}(List<{primary_attr_type}> {primary_attr}List);'''
    
    elif(action in ['clear']):
        text3 += f'''
    // {action_zh}
    public void {action}{class_name}(){{
        {project_name}Mapper.{action}{class_name}();
        return ;
    }}
    '''
        text4 += f'''
        // {action_zh}
        public void {action}{class_name}();
    '''


# 依次查询
text521 = f'''
    <!-- 依次查询 -->
    <select id="select{class_name}" parameterType="{primary_attr_type}" resultType="{class_name}">
        SELECT * FROM `{table_name}`
        WHERE `{primary_field}` = #{{{primary_attr}}}
    </select>
'''

# 批量查询
text522 = f'''
    <!-- 批量查询 -->
    <select id="batchSelect{class_name}" resultType="{class_name}">
        SELECT * FROM `{table_name}`
        WHERE `{primary_field}` IN
        <foreach collection="list" item="item" open="(" close=")" separator=",">
            #{{item}}
        </foreach>
    </select>
'''

# 依次插入
text531 = f'''
    <!-- 依次插入 -->
    <insert id="insert{class_name}" parameterType="{class_name}">
        INSERT INTO `{table_name}` {text501}
        VALUES {text502}
    </insert>
'''

# 批量插入
text532 = f'''
    <!-- 批量插入 -->
    <insert id="batchInsert{class_name}" parameterType="java.util.List">
        INSERT INTO `{table_name}` {text501}
        VALUES
        <foreach collection="list" item="item" index="index" separator="," >
            {text503}
        </foreach>
    </insert>
'''

# 依次更新
text541 = f'''
    <!-- 依次更新 -->
    <update id="update{class_name}" parameterType="{class_name}">
        UPDATE `{table_name}`
        SET {text504}
        WHERE `{primary_field}` = #{{{primary_attr}}}
    </update>
'''

# 批量更新
text542 = f'''
    <!-- 批量更新 -->
    <update id="batchUpdate{class_name}" parameterType="java.util.List">
        UPDATE `{table_name}`
        <trim prefix="SET" suffixOverrides=",">
        {text505}
        </trim>
        WHERE `{primary_field}` IN
        <foreach collection="list" item="item" open="(" close=")" separator=",">
            #{{item.{primary_attr}}}
        </foreach>
    </update>
'''

# 依次插入或更新
text551 = f'''
    <!-- 依次插入或更新 -->
    <insert id="insertOrUpdate{class_name}" parameterType="{class_name}">
        INSERT INTO `{table_name}` {text501}
        VALUES {text502}
        ON DUPLICATE KEY UPDATE {text506}
    </insert>
'''

# 批量插入或更新
text552 = f'''
    <!-- 批量插入或更新 -->
    <insert id="batchInsertOrUpdate{class_name}" parameterType="java.util.List">
        INSERT INTO `{table_name}` {text501}
        VALUES
        <foreach collection="list" item="item" separator=",">
            {text503}
        </foreach>
        ON DUPLICATE KEY UPDATE {text506}
    </insert>
'''

# 依次删除
text561 = f'''
    <!-- 依次删除 -->
    <delete id="delete{class_name}" parameterType="{primary_attr_type}">
        DELETE FROM `{table_name}`
        WHERE `{primary_field}` = #{{{primary_attr}}}
    </delete>
'''

# 批量删除
text562 = f'''
    <!-- 批量删除 -->
    <delete id="batchDelete{class_name}" parameterType="java.util.List">
        DELETE FROM `{table_name}`
        WHERE `{primary_attr}` IN
        <foreach collection="list" item="item" open="(" close=")" separator=",">
            #{{item}}
        </foreach>
    </delete>
'''

# 清空
text571 = f'''
    <!-- 清空 -->
    <update id="clear{class_name}">
        TRUNCATE TABLE `{table_name}`
    </update>
'''

with open(params['output_name_1'],"w",encoding='utf-8') as f:

    f.write('\n\n'.join([
        text11,
        text12,
        text13 + text14,
        text15,
        text16,
        text17,
        text18
    ]))

    f.flush() # 写入硬盘            
    f.close() # 关闭文件

with open(params['output_name_2'],"w",encoding='utf-8') as f:

    f.write(''.join([
        text2
    ]))

    f.flush() # 写入硬盘            
    f.close() # 关闭文件

with open(params['output_name_3'],"w",encoding='utf-8') as f:

    f.write(''.join([
        text3
    ]))

    f.flush() # 写入硬盘            
    f.close() # 关闭文件

with open(params['output_name_4'],"w",encoding='utf-8') as f:

    f.write(''.join([
        text4
    ]))

    f.flush() # 写入硬盘            
    f.close() # 关闭文件


with open(params['output_name_5'],"w",encoding='utf-8') as f:
    f.write(''.join([
        text521,
        text522,
        text531,
        text532,
        text541,
        text542,
        text551,
        text552,
        text561,
        text562,
        text571
    ]))
    f.flush() # 写入硬盘            
    f.close() # 关闭文件

with open(params['output_name_6'],"w",encoding='utf-8') as f:
    f.write(''.join([
        text61,
    ]))
    f.flush() # 写入硬盘            
    f.close() # 关闭文件