from typing import List
from fastapi import FastAPI, Depends, File, Request, APIRouter
import uvicorn
from model import upload


app = FastAPI(
    title="Forever AI",
    description="Forever AI API",
    version="0.1.0",
    max_upload_size=30 * 1024 * 1024,
)
app.include_router(upload)

@app.get('/')
async def pdfLoading():
    return {'message' : "pdfLoading"}