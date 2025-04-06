import httpx
from fastapi import HTTPException

async def verify_token(token: str):
    async with httpx.AsyncClient() as client:
        try:
            response = await client.post(
                "https://fourever.duckdns.org/api/upload/isAvailable",
                headers={"Authorization": f"Bearer {token}"},
                timeout=10.0
            )
            
            if response.json().get("status") == 200:
                # 백엔드에서 토큰 검증 성공
                if response.json().get("data").get("isAvailable") == True:
                    return True
                return False
                
            elif response.json().get("status") == 401:
                return {
                    "status": 401,
                    "message": "인증되지 않은 토큰입니다",
                    "data": None
                }
            elif response.json().get("status") == 403:
                return {
                    "status": 403,
                    "message": "토큰의 권한이 부족합니다",
                    "data": None
                }
            elif response.json().get("status") == 419:
                return {
                    "status": 419,
                    "message": "토큰이 만료되었습니다",
                    "data": None
                }
            else:
                return {
                    "status": 500,
                    "message": "토큰 검증 중 오류가 발생했습니다",
                    "data": None
                }
                
        except httpx.RequestError:
            return {
                "status": 503,
                "message": "인증 서버와 통신 중 오류가 발생했습니다",
                "data": None
            }