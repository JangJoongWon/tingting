import styles from './Navbar.module.css'
import { logo } from '../../assets/Images/index'
import { useNavigate, useLocation } from 'react-router-dom';
import { useEffect, useState } from 'react'


export default function Navbar(){
    
    const location = useLocation();
    const [isLoginPage, setIsLoginPage] = useState(location.pathname === '/login');
  
    useEffect(()=>{
      setIsLoginPage( window.location.pathname=== '/login')
    },[location.pathname])

    const navigate = useNavigate()
    const goToOtherPage = (pageName:string) => {
        navigate(`/${pageName}`);
    }

    if (isLoginPage) {
        return null; // 로그인 페이지에서 네비게이션 바를 숨김
      }
    
    return (
    <div
    className={styles.container} 
    >

        <img
        onClick={() => goToOtherPage('')}
        className={styles.logo}
        src={logo} alt="logo" /> 
        
        <div
        onClick={() => goToOtherPage('search')}
        className={styles.logout}>
            검색페이지
        </div>

        <div
        onClick={() => goToOtherPage('mypage')}
        className={styles.logout}>
            마이페이지
        </div>

        <div
        onClick={() => goToOtherPage('concert/detail')}
        className={styles.logout}>
            콘서트 상세 
        </div>

        <div
        onClick={() => goToOtherPage('login')}
        className={styles.logout}>
            로그인
        </div>
    </div>
    );
}