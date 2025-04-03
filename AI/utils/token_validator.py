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
                if response.json().get("isAvailable") == True:
                    return True
                return False
                
            elif response.json().get("status") == 401:
                raise HTTPException(
                    status_code=401,
                    detail="인증되지 않은 토큰입니다"
                )
            elif response.json().get("status") == 403:
                raise HTTPException(
                    status_code=403,
                    detail="토큰의 권한이 부족합니다"
                )
            elif response.json().get("status") == 419:
                raise HTTPException(
                    status_code=419,
                    detail="토큰이 만료되었습니다"
                )
            else:
                raise HTTPException(
                    status_code=500,
                    detail="토큰 검증 중 오류가 발생했습니다"
                )
                
        except httpx.RequestError:
            raise HTTPException(
                status_code=503,
                detail="인증 서버와 통신 중 오류가 발생했습니다"
            )