// src/main/frontend/src/App.js

import React, {useState,useEffect}from 'react';
import axios from 'axios';
import './App.css';

const RecordForm=({numList, setNumList})=>{

    const[posts,setPosts]=useState([]);
    useEffect(()=>{axios({method:'GET',
                         url:''})})

    const [num,setNum]=useState(0);

    return (<div>
        <div>
            현재 숫자 : {num}
        </div>
            <button onClick={()=>setNum(num+1)}>숫자 증가</button>
            <button onClick={()=>setNum(num-1)}>숫자 감소</button>
            <button onClick={()=>setNum(0)}>초기화</button>
        <hr/>
            <button onClick={()=>setNumList([...numList,num])}>숫자 기록</button>
            <button onClick={()=>setNumList([])}>기록 초기화</button>
    </div>)
}

const RecordList=({numList})=>{
    return(
    <div>
            <h2>기록된 숫자</h2>
            {numList.length > 0 ? <div>기록 있음</div>:<div>기록 없음</div>}
    </div>)
}

const App = () => {

const[numList,setNumList]=useState([]);

//----------------------------------------------------------------------------
//텍스트입력
const[text,setText]=useState("11");
const[edit,setEdit]=useState(false);

let content=<div>{text}
<button onClick={()=>setEdit(true)}>수정</button>
</div>

if(edit){
    content=<div>
        <input type="text"
        value={text} 
        onChange={(e)=>{
            setText(e.target.value);
        }}/>
        <button onClick={()=>setEdit(false)}>수정</button>
    </div>
}
//----------------------------------------------------------------------------


return(<> <div style={{margin:"40px auto",
                    width:"800px",
                    textAlign:"center"
                    }}>
        <RecordForm numList={numList} setNumList={setNumList}/>
        <RecordList numList={numList}/>
        </div> ,{content}</>);
}

export default App;
